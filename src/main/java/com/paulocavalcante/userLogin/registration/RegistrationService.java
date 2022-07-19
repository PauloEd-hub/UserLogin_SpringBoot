package com.paulocavalcante.userLogin.registration;

import com.paulocavalcante.userLogin.AppUser.AppUser;
import com.paulocavalcante.userLogin.AppUser.AppUserRole;
import com.paulocavalcante.userLogin.AppUser.AppUserService;
import com.paulocavalcante.userLogin.registration.token.ConfirmationToken;
import com.paulocavalcante.userLogin.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return  "confirmed";
    }
}
