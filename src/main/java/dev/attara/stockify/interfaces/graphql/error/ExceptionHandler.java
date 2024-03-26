package dev.attara.stockify.interfaces.graphql.error;

import dev.attara.stockify.domain.exception.*;
import graphql.GraphQLError;
import lombok.NonNull;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ExceptionHandler {

    @GraphQlExceptionHandler({UserNotFoundException.class, ProductNotFoundException.class, OrderNotFoundException.class})
    public GraphQLError handleNotFoundExceptions(@NonNull RuntimeException exception) {
        return GraphQLError.newError().errorType(ErrorType.NOT_FOUND)
                .message(exception.getMessage()).build();
    }
    @GraphQlExceptionHandler({InsufficientStockException.class, UserExistsException.class ,IllegalArgumentException.class})
    public GraphQLError handleBadRequest(@NonNull RuntimeException exception) {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST)
                .message(exception.getMessage()).build();
    }
    @GraphQlExceptionHandler({NotAuthorizedException.class, AuthenticationException.class})
    public GraphQLError handleAuthorizationException (@NonNull AuthenticationException exception) {
        return GraphQLError.newError().errorType(ErrorType.UNAUTHORIZED)
                .message(exception.getMessage()).build();
    }

}
