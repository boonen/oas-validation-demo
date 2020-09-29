package com.geodan.cloud.demo.jpa;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentMetadataRepository extends PagingAndSortingRepository<DocumentMetadata, Long> {

    DocumentMetadata findByOrganisationIdAndApiNameAndName(String organisationId, String apiName, String name);

    List<DocumentMetadata> findByOrganisationIdAndApiName(String organisationId, String apiName);

    @Query(value = "select d from DocumentMetadata d where (d.organisationId=?1 and d.api.name =?2) and (d.name like %?3%)",
            countQuery = "select count(d) from DocumentMetadata d where (d.organisationId=?1 and d.api.name =?2) and (d.name like %?3%)")
    Page<DocumentMetadata> findUsingFilter(String organidationId, String apiName, String query, Pageable pageable);

}
