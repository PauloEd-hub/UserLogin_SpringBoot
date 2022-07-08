package com.paulocavalcante.userLogin.registration;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String firstname;
    private final String lastname;
    private final String email;
    private final String password;
}
