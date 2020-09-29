package com.geodan.cloud.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

@Data
@Builder
public class FeatureCollection<T extends Feature> {
    
    private final String type = "FeatureCollection";
    
    private Collection<T> features;
    
}
