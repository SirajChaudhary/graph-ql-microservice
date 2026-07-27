package com.sirajchaudhary.library.exception;

import com.sirajchaudhary.library.dto.ValidationError;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(
            Throwable ex,
            DataFetchingEnvironment environment) {

        if (ex instanceof ResourceNotFoundException resourceNotFoundException) {

            return GraphqlErrorBuilder.newError(environment)
                    .message(resourceNotFoundException.getMessage())
                    .errorType(ErrorType.NOT_FOUND)
                    .build();
        }

        if (ex instanceof ConstraintViolationException validationException) {

            List<ValidationError> errors = validationException.getConstraintViolations()
                    .stream()
                    .map(violation -> new ValidationError(
                            extractFieldName(violation.getPropertyPath().toString()),
                            violation.getMessage()))
                    .toList();

            return GraphqlErrorBuilder.newError(environment)
                    .message("Validation failed")
                    .errorType(ErrorType.BAD_REQUEST)
                    .extensions(Map.of("errors", errors))
                    .build();
        }

        if (ex instanceof IllegalArgumentException illegalArgumentException) {

            return GraphqlErrorBuilder.newError(environment)
                    .message(illegalArgumentException.getMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
        }

        if (ex instanceof DataIntegrityViolationException) {

            return GraphqlErrorBuilder.newError(environment)
                    .message("Duplicate value already exists.")
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
        }

        return GraphqlErrorBuilder.newError(environment)
                .message("Unexpected error occurred")
                .errorType(ErrorType.INTERNAL_ERROR)
                .build();
    }

    private String extractFieldName(String propertyPath) {

        int index = propertyPath.lastIndexOf('.');

        return index >= 0
                ? propertyPath.substring(index + 1)
                : propertyPath;
    }
}