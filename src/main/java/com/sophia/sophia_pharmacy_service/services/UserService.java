package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.audit.AuditContext;
import com.sophia.sophia_pharmacy_service.audit.AuditEvent;
import com.sophia.sophia_pharmacy_service.auth.GoogleAuth;
import com.sophia.sophia_pharmacy_service.auth.JwtUtil;
import com.sophia.sophia_pharmacy_service.dtos.audit.UserAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.LoginDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.UserDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.UserInfoDto;
import com.sophia.sophia_pharmacy_service.dtos.mappers.PermissionMapper;
import com.sophia.sophia_pharmacy_service.dtos.mappers.UserMapper;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionUserDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.enums.Provider;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import com.sophia.sophia_pharmacy_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil JwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoogleAuth googleAuth;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private AuditContext auditContext;

    @Transactional
    @AuditEvent
    public void register(UserDto dto) {

        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email já cadastrado");
        });

        dto.setProvider(Provider.LOCAL);

        UserAuditDto user = userMapper.toAudit(userRepository.save(userMapper.toEntity(dto)));

        auditContext.insert("user", user);
    }

    public String login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return JwtUtil.generateToken(user);
    }

    @AuditEvent
    public String loginWithGoogle(String idToken) {
        var payload = googleAuth.validate(idToken);

        String email = payload.getEmail();
        String nome = (String) payload.get("name");

        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(nome);
                newUser.setProvider(Provider.GOOGLE);

                UserAuditDto userAudit = userMapper.toAudit(newUser);

                auditContext.insert("user", userAudit);

                return userRepository.save(newUser);
            });

        return JwtUtil.generateToken(user);
    }

    public UserInfoDto userInfo(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<PermissionUserDto> permissions = permissionMapper.toUserInfoDtoList(permissionRepository.findAllByUserEmail(email));

        return new UserInfoDto(user.getUsername(), email, permissions);
    }

}