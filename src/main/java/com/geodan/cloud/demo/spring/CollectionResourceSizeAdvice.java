package com.geodan.cloud.demo.spring;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

@ControllerAdvice
public class CollectionResourceSizeAdvice implements ResponseBodyAdvice<Collection<?>> {

    public static final String X_TOTAL_COUNT = "X-Total-Count";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //Checks if this advice is applicable. 
        //In this case it applies to any endpoint which returns a collection.
        return Collection.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Collection<?> beforeBodyWrite(Collection<?> body, MethodParameter returnType, MediaType selectedContentType,
                                         Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                         ServerHttpResponse response) {
        if (!response.getHeaders().containsKey(X_TOTAL_COUNT)) {
            response.getHeaders().add(X_TOTAL_COUNT, String.valueOf(body.size()));
        }
        return body;
    }
}
