package com.geodan.cloud.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    private String type = "Feature";

    private String id;

    private double[] bbox;

    @JsonInclude()
    private Object geometry;

    private Map<String, Object> properties;

}
