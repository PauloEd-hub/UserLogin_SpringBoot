package com.paulocavalcante.userLogin.registration;

import com.paulocavalcante.userLogin.AppUser.AppUser;
import com.paulocavalcante.userLogin.AppUser.AppUserRole;
import com.paulocavalcante.userLogin.AppUser.AppUserService;
import com.paulocavalcante.userLogin.registration.token.ConfirmationToken;
import com.paulocavalcante.userLogin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
       boolean isValidEmail =  emailValidator.test(request.getEmail());

       if(!isValidEmail) {
           throw new IllegalStateException("email not valid");
       }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
