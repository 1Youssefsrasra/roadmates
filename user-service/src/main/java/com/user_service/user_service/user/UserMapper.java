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
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phonenumber(request.phonenumber() != null ? Long.parseLong(request.phonenumber()) : null)
                .birthday(request.birthday())
                .Identitycard(request.Identitycard() != null ? Long.parseLong(request.Identitycard()) : null)
                .address(request.address())
                .build();
    }
}
