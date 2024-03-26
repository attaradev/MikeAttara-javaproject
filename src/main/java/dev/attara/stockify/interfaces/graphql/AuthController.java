package dev.attara.stockify.interfaces.graphql;

import dev.attara.stockify.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import javax.naming.AuthenticationException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @MutationMapping
    public String accessToken(@Argument String email, @Argument String password) throws AuthenticationException {
        return authService.authenticate(email, password);
    }

}
