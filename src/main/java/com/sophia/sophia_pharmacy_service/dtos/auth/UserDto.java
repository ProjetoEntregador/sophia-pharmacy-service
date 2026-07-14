package com.sophia.sophia_pharmacy_service.dtos.auth;

import com.sophia.sophia_pharmacy_service.entities.enums.Provider;
import com.sophia.sophia_pharmacy_service.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String password;
    private Provider provider;

    public UserDto(User user) {
        BeanUtils.copyProperties(user,this);
    }

}