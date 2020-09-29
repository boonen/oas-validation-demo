package com.geodan.cloud.demo.jpa;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentMetadataRepository extends PagingAndSortingRepository<DocumentMetadata, Long> {

    DocumentMetadata findByOrganisationIdAndApiNameAndName(String organisationId, String apiName, String name);

}
