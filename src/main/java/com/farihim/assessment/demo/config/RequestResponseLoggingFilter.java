package com.farihim.assessment.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Filter that logs HTTP request and response details including:
 * - Request method and URI
 * - Request and response payloads
 * - Response status code
 * - Processing time
 */
@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final String EMPTY_CONTENT = "[EMPTY]";
    private static final String UNSUPPORTED_ENCODING = "[UNSUPPORTED ENCODING]";
    private static final int CONTENT_CACHE_LIMIT = 1024 * 1024;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, CONTENT_CACHE_LIMIT);
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

    private void logRequestResponse(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long timeTaken) {

        String requestBody = getContentAsString(
                request.getContentAsByteArray(),
                request.getCharacterEncoding()
        );

        String responseBody = getContentAsString(
                response.getContentAsByteArray(),
                response.getCharacterEncoding()
        );

        log.info(
                "FINISHED PROCESSING : METHOD={}; REQUEST URI={}; REQUEST PAYLOAD={}; " +
                        "RESPONSE CODE={}; RESPONSE PAYLOAD={}; TIME TAKEN={}ms",
                request.getMethod(),
                request.getRequestURI(),
                requestBody,
                response.getStatus(),
                responseBody,
                timeTaken
        );
    }

    private String getContentAsString(byte[] content, String encoding) {
        if (content == null || content.length == 0) {
            return EMPTY_CONTENT;
        }

        try {
            String charsetName = encoding != null ? encoding : StandardCharsets.UTF_8.name();
            return new String(content, charsetName);
        } catch (Exception e) {
            log.warn("Failed to decode content with encoding: {}", encoding, e);
            return UNSUPPORTED_ENCODING;
        }
    }
}