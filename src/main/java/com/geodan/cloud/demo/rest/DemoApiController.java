package com.geodan.cloud.demo.rest;

import com.atlassian.oai.validator.report.ValidationReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geodan.cloud.demo.domain.FeatureCollection;
import com.geodan.cloud.demo.domain.Location;
import com.geodan.cloud.demo.service.OpenApiProvider;
import com.geodan.cloud.demo.spring.ValidationProblem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/demo", produces = {MediaType.APPLICATION_JSON_VALUE, "application/geo+json"})
public class DemoApiController {

    private Map<String, Map<String, Location>> locations = new HashMap<>();

    private ObjectMapper objectMapper;

    private OpenApiProvider openApiProvider;

    public DemoApiController(ObjectMapper objectMapper, OpenApiProvider openApiProvider) {
        this.objectMapper = objectMapper;
        this.openApiProvider = openApiProvider;
    }

    @GetMapping("/{customerId}/locations")
    public FeatureCollection<Location> getAll(@PathVariable String customerId) {
        Collection<Location> locations = getCustomerLocations(customerId);
        FeatureCollection<Location> featureCollection = FeatureCollection.<Location>builder()
                .features(locations).build();
        return featureCollection;
    }

    @PostMapping("/{customerId}/locations")
    public ResponseEntity<Void> create(@PathVariable String customerId, @RequestBody Location location) {
        try {
            ValidationReport validationReport = openApiProvider.validate(objectMapper.writeValueAsString(location), "locations", "Location");
            if (validationReport.hasErrors()) {
                throw new ValidationProblem(URI.create(String.format("%s/demo/%s/locations", null, customerId)), validationReport);
            }
            UUID locationId = UUID.randomUUID();
            location.setId(locationId.toString());
            locations.putIfAbsent(customerId, new HashMap<>());
            locations.get(customerId).put(locationId.toString(), location);
            return ResponseEntity.noContent()
                    .build();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON.");
        }

    }

    private Collection<Location> getCustomerLocations(String customerId) {
        Map<String, Location> customerLocations = this.locations.get(customerId);
        if (customerLocations != null) {
            return customerLocations.values();
        } else {
            return Collections.emptyList();
        }
    }

}
