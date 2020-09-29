package com.geodan.cloud.demo.service;

import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.MessageResolver;
import com.atlassian.oai.validator.report.ValidationReport;
import com.atlassian.oai.validator.schema.SchemaValidator;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.atlassian.oai.validator.schema.SchemaValidator.ADDITIONAL_PROPERTIES_KEY;

@Slf4j
@Service
public class OpenApiProvider {

    private final String defaultApi = "locations";

    final MessageResolver messageResolver;

    final OpenAPIParser openAPIParser = new OpenAPIParser();

    final Map<String, SwaggerParseResult> apiDefinitions = new HashMap<>();

    final ParseOptions parseOptions = new ParseOptions();

    public OpenApiProvider() {
        parseOptions.setResolve(true);
        parseOptions.setResolveCombinators(true);
        LevelResolver levelResolver = LevelResolver.create()
                .withLevel("validation.request.parameter.query.unexpected", ValidationReport.Level.IGNORE)
                .withLevel(ADDITIONAL_PROPERTIES_KEY, ValidationReport.Level.IGNORE)
                .build();
        messageResolver = new MessageResolver(levelResolver);
    }

    public ValidationReport validate(String json, String apiName, String typeName) {
        SwaggerParseResult apiDefinition = getSchemaValidator(apiName);
        SchemaValidator validator = new SchemaValidator(apiDefinition.getOpenAPI(), messageResolver);

        ValidationReport validationReport = validator.validate(json, apiDefinition.getOpenAPI().getComponents().getSchemas().get(typeName), typeName);
        return validationReport;
    }

    private static String readApiDefinition(String apiName) {
        try {
            File apiDefinition = ResourceUtils.getFile(String.format("classpath:static/apis/%s.yaml", apiName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(apiDefinition)));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Resource 'classpath:static/apis/%s.yaml\' not found.", apiName));
        }
    }
    
    private SwaggerParseResult getSchemaValidator(String apiName) {
        SwaggerParseResult apiDefinition;
        if (apiDefinitions.containsKey(apiName)) {
            apiDefinition = apiDefinitions.get(apiName);
        } else {
            apiDefinition = openAPIParser.readContents(readApiDefinition(apiName), null, parseOptions);
            apiDefinitions.put(apiName, apiDefinition);
        }
        return apiDefinition;
    }

}
