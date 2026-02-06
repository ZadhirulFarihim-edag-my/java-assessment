package com.farihim.assessment.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            logRequestResponse(requestWrapper, responseWrapper, timeTaken);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequestResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long timeTaken) {
        String requestBody = getContent(request.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getContent(response.getContentAsByteArray(), response.getCharacterEncoding());

        log.info("FINISHED PROCESSING : METHOD={}; REQUEST URI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE PAYLOAD={}; TIME TAKEN={}ms",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody, timeTaken);
    }

    private String getContent(byte[] content, String contentEncoding) {
        if (content == null || content.length == 0) {
            return "[EMPTY]";
        }
        try {
            return new String(content, contentEncoding != null ? contentEncoding : StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            return "[UNSUPPORTED ENCODING]";
        }
    }
}
