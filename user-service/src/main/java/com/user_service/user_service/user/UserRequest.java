package com.user_service.user_service.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

// Fields in a record are final, meaning the values cannot be modified after the object is created.
public record UserRequest(

        String id,
        @NotNull(message = "User firstname is required")
        String firstname,
        @NotNull(message = "User lastname is required")
        String lastname,
        @NotNull(message = "User Email is required")
        @Email(message = "user Email is not a valid email address")
        String email,

        @NotNull(message = "Phone number is required")
        //@Pattern est utilisée pour valider qu'une chaîne respecte une expression régulière (regex).
        @Pattern(regexp = "\\d{8}", message = "Phone number must be exactly 8 digits")
        String phonenumber,

        @NotNull(message = "User birthday is required")
        @PastOrPresent(message = "Birthday must be a past or present date")
        LocalDate birthday,

        @NotNull(message = "Identity card number is required")
        @Pattern(regexp = "\\d{8}", message = "Identity card number must be exactly 8 digits")
        String Identitycard,
        Address address
) {


}
