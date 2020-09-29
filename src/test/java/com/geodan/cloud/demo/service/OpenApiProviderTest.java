package com.geodan.cloud.demo.service;

import com.atlassian.oai.validator.report.ValidationReport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiProviderTest {

    private OpenApiProvider openApiProvider = new OpenApiProvider();

    @Test
    void validate() {
        // Invalid ID and missing required fields
        String json = "{\"id\":123}";
        ValidationReport result = openApiProvider.validate(json, "locations", "Location");
        assertThat(result).isNotNull();
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getMessages().size()).isEqualTo(2);

        // Valid feature with (empty) no geometry
        json = "{\"type\": \"Feature\",\"id\":\"123\",\"properties\": {}, \"geometry\": null}";
        result = openApiProvider.validate(json, "locations", "Location");
        assertThat(result).isNotNull();
        assertThat(result.hasErrors()).isFalse();

        // Invalid Geometry specification
        json = "{\"type\": \"Feature\",\"id\":\"123\",\"properties\": {}, \"geometry\": {\"type\": \"MyPoint\"}}";
        result = openApiProvider.validate(json, "locations", "Location");
        assertThat(result).isNotNull();
        assertThat(result.hasErrors()).isTrue();

        // Fully valid
        json = "{\"type\": \"Feature\",\"id\":\"123\",\"properties\": {}, \"geometry\": {\"type\":\"Point\",\"coordinates\":[5.30073,51.69267]}}";
        result = openApiProvider.validate(json, "locations", "Location");
        assertThat(result).isNotNull();
        assertThat(result.hasErrors()).isFalse();
    }
}