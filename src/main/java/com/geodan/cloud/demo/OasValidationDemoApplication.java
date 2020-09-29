package com.geodan.cloud.demo;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class OasValidationDemoApplication {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addCustomBigDecimalDeserialization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.modules(
                new ProblemModule(),
                new ConstraintViolationProblemModule(),
                new JtsModule());
    }

    @Configuration
    public class FaviconConfiguration {

        @Bean
        public SimpleUrlHandlerMapping customFaviconHandlerMapping() {
            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setOrder(Integer.MIN_VALUE);
            mapping.setUrlMap(Collections.singletonMap(
                    "/favicon.ico", faviconRequestHandler()));
            return mapping;
        }

        @Bean
        protected ResourceHttpRequestHandler faviconRequestHandler() {
            ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
            ClassPathResource classPathResource = new ClassPathResource("static/");
            List<Resource> locations = Arrays.asList(classPathResource);
            requestHandler.setLocations(locations);
            return requestHandler;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(OasValidationDemoApplication.class, args);
    }

}
