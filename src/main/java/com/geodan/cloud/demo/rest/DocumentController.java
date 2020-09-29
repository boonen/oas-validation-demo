package com.geodan.cloud.demo.rest;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import com.geodan.cloud.demo.service.DocumentService;
import com.geodan.cloud.demo.service.OpenApiProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/{apiName}", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

    //TODO get customer from Authenticated Principal
    private static final String ORGANISATION_ID = "customer1";

    private final DocumentService documentService;

    private final OpenApiProvider openApiProvider;

    public DocumentController(DocumentService documentService, OpenApiProvider openApiProvider) {
        this.documentService = documentService;
        this.openApiProvider = openApiProvider;
    }

    @GetMapping
    public List<DocumentMetadata> getDocuments(@PathVariable String apiName) {

        return documentService.getDocuments(ORGANISATION_ID, apiName);
    }

    @GetMapping("/{name}")
    public DocumentMetadata getDocument(@PathVariable String apiName, @PathVariable String name) {
        DocumentMetadata document = documentService.getDocument(ORGANISATION_ID, apiName, name);
        if (document == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }
        return document;
    }

    @PostMapping
    public ResponseEntity<Void> createDocument(@PathVariable String apiName, @Valid @RequestBody DocumentMetadata documentMetadata) {
        documentMetadata.setOrganisationId(ORGANISATION_ID);
        documentService.save(documentMetadata);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(documentMetadata.getName()).toUri();
        return ResponseEntity.created(location)
                .build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String apiName, @PathVariable String name) {
        documentService.delete(ORGANISATION_ID, apiName, name);
        return ResponseEntity.noContent()
                .build();
    }

}
