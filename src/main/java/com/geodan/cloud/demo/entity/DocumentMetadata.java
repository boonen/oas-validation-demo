package com.geodan.cloud.demo.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class DocumentMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id")
    Long internalId;

    @JsonIgnore
    String organisationId;

    @Embedded
    @Valid
    Api api;

    @NotEmpty
    @JsonProperty("id")
    @JsonAlias("name")
    String name;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Api {

        @Column(name = "api_name")
        @NotEmpty
        private String name;

        @Column(name = "api_version")
        private String version;

    }

}
