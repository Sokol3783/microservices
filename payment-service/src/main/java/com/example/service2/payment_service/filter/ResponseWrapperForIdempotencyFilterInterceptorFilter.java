package com.example.service2.payment_service.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ResponseWrapperForIdempotencyFilterInterceptorFilter implements Filter {

    public static final String WRAPPED_RESPONSE_ATTRIBUTE = "WrappedResponse";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
           wrapResponseForNonIdempotentMethods(httpRequest, response, chain);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void wrapResponseForNonIdempotentMethods(HttpServletRequest httpRequest, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        var method = HttpMethod.valueOf(httpRequest.getMethod());
        if (method.equals(HttpMethod.POST)) {
            var wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);
            httpRequest.setAttribute(WRAPPED_RESPONSE_ATTRIBUTE, wrappedResponse);

            try {
                chain.doFilter(httpRequest, wrappedResponse);
            } finally {
                wrappedResponse.copyBodyToResponse();
            }
        } else{
            doFilter(httpRequest, response, chain);
        }
   }

}
