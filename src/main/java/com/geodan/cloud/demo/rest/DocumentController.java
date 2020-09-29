package com.geodan.cloud.demo.rest;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import com.geodan.cloud.demo.service.DocumentService;
import com.geodan.cloud.demo.service.OpenApiProvider;
import com.geodan.cloud.demo.spring.CollectionResourceSizeAdvice;
import com.geodan.cloud.demo.spring.ReactAdminJsonPlaceholderFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<List<DocumentMetadata>> getDocuments(@PathVariable String apiName,
                                                               @RequestParam(required = false, defaultValue = "") String q,
                                                               @RequestParam(name = "_start", required = false) Integer start, @RequestParam(name = "_end", required = false) Integer end,
                                                               @RequestParam(name = "_order", required = false) String order, @RequestParam(name = "_sort", required = false) String sort) {
        if (StringUtils.hasText(q) || StringUtils.hasText(sort) || end != null) {
            ReactAdminJsonPlaceholderFilter filter = ReactAdminJsonPlaceholderFilter.builder()
                    .q(q)
                    .start(start)
                    .end(end)
                    .order(order)
                    .sort(sort)
                    .build();
            Page<DocumentMetadata> filteredDocuments = documentService.getFilteredDocuments(ORGANISATION_ID, apiName, filter);
            return ResponseEntity.ok()
                    .header(CollectionResourceSizeAdvice.X_TOTAL_COUNT, String.valueOf(filteredDocuments.getTotalElements()))
                    .body(filteredDocuments.getContent());
        }
        return ResponseEntity.ok(documentService.getDocuments(ORGANISATION_ID, apiName));
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
    public ResponseEntity<?> createDocument(@PathVariable String apiName, @Valid @RequestBody DocumentMetadata documentMetadata) {
        documentMetadata.setOrganisationId(ORGANISATION_ID);
        documentService.save(documentMetadata);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(documentMetadata.getName()).toUri();
        return ResponseEntity.ok(documentMetadata);
    }

    @PutMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDocument(@PathVariable String apiName, @PathVariable String name, @Valid @RequestBody DocumentMetadata documentMetadata) {
        DocumentMetadata existingDocument = documentService.getDocument(ORGANISATION_ID, apiName, name);
        if (existingDocument != null) {
            documentMetadata.setInternalId(existingDocument.getInternalId());
            documentMetadata.setOrganisationId(existingDocument.getOrganisationId());
            documentService.save(documentMetadata);
            return ResponseEntity.ok(documentMetadata);
        } else {
            return createDocument(apiName, documentMetadata);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String apiName, @PathVariable String name) {
        documentService.delete(ORGANISATION_ID, apiName, name);
        return ResponseEntity.noContent()
                .build();
    }

}

