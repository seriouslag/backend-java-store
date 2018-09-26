package com.chiefsretro.beans;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chiefsretro.services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    static boolean checkAuth = false;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final CountDownLatch firebaseLatch = new CountDownLatch(1);
        if (!request.getServletPath().contains("admin")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!checkAuth) {
            request.setAttribute("uid", "testUID");
            request.setAttribute("role", "admin");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            filterChain.doFilter(request, response);
        } else {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String idToken = authHeader.substring(7);

            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                String uid = decodedToken.getUid();
                request.setAttribute("uid", uid);

                FirebaseService.addListenerForUserRole(uid, value -> {
                    try {
                        request.setAttribute("role", value);
                        filterChain.doFilter(request, response);
                        firebaseLatch.countDown();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                });
                // Latch will wait for one second or until countdown amount is satisfied
                firebaseLatch.await(1L, TimeUnit.SECONDS);
                return;

            } catch (final com.google.firebase.auth.FirebaseAuthException e) {
                throw new ServletException("Invalid token");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}