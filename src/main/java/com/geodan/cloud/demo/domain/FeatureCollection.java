package com.geodan.cloud.demo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class FeatureCollection<T extends Feature> {
    
    private final String type = "FeatureCollection";
    
    private Collection<T> features;
    
}
