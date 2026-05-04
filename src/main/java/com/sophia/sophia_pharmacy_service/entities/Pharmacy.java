package com.sophia.sophia_pharmacy_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="tb_pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @NotBlank(message = "É preciso informar um telefone")
    private String phone;

    @NotBlank(message = "É preciso informar um endereço")
    private String adress;

    private String city;

    private double latitude;

    private double longitude;
}
