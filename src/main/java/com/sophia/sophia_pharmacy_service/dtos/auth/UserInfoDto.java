package com.sophia.sophia_pharmacy_service.dtos.auth;

import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private List<PermissionUserDto> permissions;
}
