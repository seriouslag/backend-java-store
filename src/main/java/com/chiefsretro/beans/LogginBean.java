package com.chiefsretro.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;


@Configuration
public class LogginBean {

    @Bean
    public Filter loggingFilter() {
        return new AbstractRequestLoggingFilter() {
            private final Logger log = LoggerFactory
                    .getLogger(LogginBean.class);

            {
                setIncludeClientInfo(true);
                setIncludeQueryString(true);
                setIncludePayload(true);
            }

            @Override
            protected void beforeRequest(HttpServletRequest request, String message) {
                // not needed
            }

            @Override
            protected void afterRequest(HttpServletRequest request, String message) {
                log.info(message);
            }
        };
    }

}
