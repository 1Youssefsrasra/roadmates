package com.user_service.user_service.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
//Permet d’utiliser le design pattern Builder pour créer des instances de l'objet de manière fluide
@Builder
//Génère automatiquement des méthodes "get et set " pour tous les champs.
@Setter
@Getter
//Indique que cette classe représente une collection MongoDB
@Document
@Data
public class user {
    //l'attribut id comme l'identifiant unique de l'objet dans une base de données,(Primary Key)
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password; // Nouveau champ pour le mot de passe
    private boolean isLoggedIn; // Indique si l'utilisateur est connecté
    private Long phonenumber;
    private LocalDate birthday;
    private Long Identitycard;
    private Address address;


}
