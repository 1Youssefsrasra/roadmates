package com.user_service.user_service.user;

import org.springframework.stereotype.Component;

@Component
//la classe CustomerMapper est utilisée pour convertir un objet de type CustomerRequest (probablement un DTO) en un objet de type Customer (une entité métier ou de base de données).
public class UserMapper {

    //La méthode utilise un Builder Pattern (probablement généré par Lombok via l’annotation @Builder).
    //Le builder() permet de construire un objet user en initialisant ses champs de manière fluide et lisible.
    //Les champs du UserRequest (DTO) sont transférés dans le user (entité).
    public user toUser(UserRequest request) {
        if (request == null) {
            return null;
        }

        return user.builder()
                .id(request.id()) // Direct assignment; id is already a String.
                .firstname(request.firstname()) // Direct assignment; firstname is already a String.
                .lastname(request.lastname()) // Direct assignment; lastname is already a String.
                .email(request.email()) // Direct assignment; email is already a String.
                .password(request.password()) // Nouveau champ
                .phonenumber(request.phonenumber()) // No parsing needed; phonenumber is already a Long.
                .birthday(request.birthday()) // Direct assignment; birthday is already a LocalDate.
                .Identitycard(request.IdentityCard()) // No parsing needed; Identitycard is already a Long.
                .address(request.address()) // Assuming Address mapping is correct.
                .build();
    }

    public UserResponse fromUser(user user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
                user.getId(),          // String
                user.getFirstname(),   // String
                user.getLastname(),    // String
                user.getEmail(),       // String
                user.getPhonenumber(), // Long
                user.getBirthday(),    // LocalDate
                user.getIdentitycard(),// Long
                user.getAddress()      // Address
        );
    }
}
