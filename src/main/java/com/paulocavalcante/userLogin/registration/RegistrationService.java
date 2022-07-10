package com.paulocavalcante.userLogin.registration;

import com.paulocavalcante.userLogin.AppUser.AppUser;
import com.paulocavalcante.userLogin.AppUser.AppUserRole;
import com.paulocavalcante.userLogin.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

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
