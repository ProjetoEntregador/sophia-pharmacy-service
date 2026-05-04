package com.sophia.sophia_pharmacy_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String username;

    @NotBlank
    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    public User(String username,String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
