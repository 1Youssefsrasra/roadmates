package com.user_service.user_service.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

// Fields in a record are final, meaning the values cannot be modified after the object is created.
public record UserRequest(

        String id,
        @NotNull(message = "user firstname is required")
        String firstname,
        @NotNull(message = "user lasttname is required")
        String lastname,
        @NotNull(message = "user Email is required")
        @Email(message = "user Email is not a valid email address")
        String email,
        @NotNull(message = "Password is required")
        String password, // Nouveau champ
        Long phonenumber,
        LocalDate birthday,
        Long IdentityCard,
        Address address
) {
}