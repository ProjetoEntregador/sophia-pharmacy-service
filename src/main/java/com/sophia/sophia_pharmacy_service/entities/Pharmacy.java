package com.sophia.sophia_pharmacy_service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="tb_pharmacies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone"})
})
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @NotBlank(message = "É preciso informar um telefone")
    private String phone;

    @NotBlank(message = "É preciso informar um endereço")
    private String address;

    @NotBlank(message = "É preciso informar uma cidade")
    private String city;

    @NotNull(message = "Latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude mínima é -90")
    @DecimalMax(value = "90.0", message = "Latitude máxima é 90")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "Longitude mínima é -180")
    @DecimalMax(value = "180.0", message = "Longitude máxima é 180")
    private Double longitude;
}
