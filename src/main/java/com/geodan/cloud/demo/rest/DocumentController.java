package com.geodan.cloud.demo.rest;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import com.geodan.cloud.demo.service.DocumentService;
import com.geodan.cloud.demo.service.OpenApiProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    private DocumentService documentService;

    private OpenApiProvider openApiProvider;

    public DocumentController(DocumentService documentService, OpenApiProvider openApiProvider) {
        this.documentService = documentService;
        this.openApiProvider = openApiProvider;
    }

    @GetMapping("/{apiName}/{name}")
    public DocumentMetadata getDocument(@PathVariable String apiName, @PathVariable String name) {
        //TODO get customer from Autenticated Principal
        final String customerId = "customer1";
        DocumentMetadata document = documentService.getDocument(customerId, apiName, name);
        if (document == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        return document;
    }

}
