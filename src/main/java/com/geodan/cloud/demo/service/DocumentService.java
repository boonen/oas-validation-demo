package com.geodan.cloud.demo.service;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import com.geodan.cloud.demo.jpa.DocumentMetadataRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    
    private DocumentMetadataRepository documentMetadataRepository;

    public DocumentService(DocumentMetadataRepository documentMetadataRepository) {
        this.documentMetadataRepository = documentMetadataRepository;
    }

    public DocumentMetadata getDocument(String organisationId, String apiName, String name) {
        return documentMetadataRepository.findByOrganisationIdAndApiNameAndName(organisationId, apiName, name);
    }
    
}
