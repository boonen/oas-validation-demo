package com.geodan.cloud.demo.spring;

import com.atlassian.oai.validator.report.ValidationReport;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ValidationProblem extends AbstractThrowableProblem {

    public ValidationProblem(URI type, ValidationReport validationReport) {
        super(type, "Bad request: input is invalid", Status.BAD_REQUEST,
                String.format("The provided input is not valid. Validation errors: %s.", formatValidationReport(validationReport)));
    }

    private static String formatValidationReport(ValidationReport validationReport) {
        if(validationReport != null) {
            StringBuilder sb = new StringBuilder();
            for(ValidationReport.Message validationMessage: validationReport.getMessages()) {
                for(ValidationReport.Message nestedMessage: validationMessage.getNestedMessages()) {
                    if(sb.length() > 0) {
                        sb.append(", ");
                    }
                    sb.append(nestedMessage.getMessage().replace("Instance type (null) ", ""));
                }
            }
            return sb.toString();
        }
        return null;
    }
    
}
