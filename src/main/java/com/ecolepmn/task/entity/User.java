package com.ecolepmn.task.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotNull(message = "L'Email ne doit pas être vide")
    @Email(message = "Entrer un email valide")
    private String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be atleast 5 characters")
    private String password;

}
