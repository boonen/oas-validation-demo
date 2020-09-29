package com.geodan.cloud.demo.service;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import com.geodan.cloud.demo.jpa.DocumentMetadataRepository;
import com.geodan.cloud.demo.spring.ReactAdminJsonPlaceholderFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DocumentService {

    private final DocumentMetadataRepository documentMetadataRepository;

    public DocumentService(DocumentMetadataRepository documentMetadataRepository) {
        this.documentMetadataRepository = documentMetadataRepository;
    }

    public DocumentMetadata getDocument(String organisationId, String apiName, String name) {
        return documentMetadataRepository.findByOrganisationIdAndApiNameAndName(organisationId, apiName, name);
    }

    public List<DocumentMetadata> getDocuments(String organisationId, String apiName) {
        return documentMetadataRepository.findByOrganisationIdAndApiName(organisationId, apiName);
    }

    public DocumentMetadata save(DocumentMetadata documentMetadata) {
        return documentMetadataRepository.save(documentMetadata);
    }

    public void delete(String organisationId, String apiName, String name) {
        DocumentMetadata document = getDocument(organisationId, apiName, name);
        if (document != null) {
            documentMetadataRepository.delete(document);
        }
        log.info("Successfully deleted Document /{}/{}/{}.", organisationId, apiName, name);
    }

    public Page<DocumentMetadata> getFilteredDocuments(String organisationId, String apiName, ReactAdminJsonPlaceholderFilter filter) {
        Pageable pageRequest = Pageable.unpaged();
        int pageSize = filter.getEnd() - filter.getStart();
        int pageStart = filter.getStart() / pageSize;
        if (filter.getOrder() != null) {
            Sort.Direction direction = Sort.Direction.valueOf(filter.getOrder());
            pageRequest = PageRequest.of(pageStart, pageSize, direction, filter.getSort());
        } else {
            pageRequest = PageRequest.of(pageStart, pageSize);
        }
        return documentMetadataRepository.findUsingFilter(organisationId, apiName, filter.getQ(), pageRequest);
    }
}
