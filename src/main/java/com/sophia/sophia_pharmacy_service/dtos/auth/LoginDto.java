package com.sophia.sophia_pharmacy_service.dtos.auth;

import com.sophia.sophia_pharmacy_service.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;

    public LoginDto(User user) {
        BeanUtils.copyProperties(user,this);
    }

}
