package com.chiefsretro.beans;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class ipFilter extends OncePerRequestFilter{
    private String[] allowedIps = {"0:0:0:0:0:0:0:1", "127.0.0.1", "0.0.0.0",
            //Stripe
            "50.18.212.157", "50.18.212.223", "52.25.214.31",
            "52.26.11.205", "52.26.14.11", "52.8.19.58", "52.8.8.189", "54.149.153.72", "54.187.182.230",
            "54.187.199.38", "54.187.208.163", "54.67.48.128", "54.67.52.245", "54.68.165.206",
            "54.68.183.151", "107.23.48.182", "107.23.48.232", "54.187.174.169", "54.187.205.235",
            "54.187.216.72", "54.241.31.99", "54.241.31.102", "54.241.34.107",
            //End Stripe
            //Linode Server
            "45.79.181.42", "2600:3c03::f03c:91ff:fe1f:53ff",
            //End Linode
            //Home
            "74.131.87.185"
            //End Home
    };

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String ipAddress = httpServletRequest.getRemoteAddr();
        int port = httpServletRequest.getLocalPort();

        if(isAllowedDomain(ipAddress, port)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendError(403, "Not a valid Domain");
        }
    }

    private boolean isAllowedDomain(String ipAddress, int port) {
        for(String ip : allowedIps) {
            if(ip.equals(ipAddress)) {
                return true;
            }
        }

        System.out.println(ipAddress + " was blocked.");
        return false;
    }
}
