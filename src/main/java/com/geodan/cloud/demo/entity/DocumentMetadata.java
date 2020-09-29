package com.geodan.cloud.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    Long id;

    @JsonIgnore
    String organisationId;

    @Embedded
    @Valid
    Api api;

    @NotEmpty
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
