package com.sophia.sophia_pharmacy_service.entities;

import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="tb_permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Permission(User user, Pharmacy pharmacy, Role role){
        this.user = user;
        this.pharmacy = pharmacy;
        this.role = role;
    }

}

