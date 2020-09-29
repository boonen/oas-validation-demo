package com.geodan.cloud.demo.jpa;

import com.geodan.cloud.demo.entity.DocumentMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentMetadataRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentMetadataRepository repository;

    @Test
    public void findByOrganisationIdAndApiNameAndName() {
        final String name = "lorem-ipsum";
        final String organisationId = "customer";
        final String apiName = "locations";
        this.entityManager.persist(DocumentMetadata.builder()
                .name(name)
                .organisationId(organisationId)
                .api(DocumentMetadata.Api.builder()
                        .name(apiName)
                        .version("1.0.0")
                        .build())
                .build());
        DocumentMetadata result = repository.findByOrganisationIdAndApiNameAndName(organisationId, apiName, name);
        assertThat(result).isNotNull();
    }

}