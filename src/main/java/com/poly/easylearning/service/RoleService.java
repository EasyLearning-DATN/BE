package com.poly.easylearning.service;

import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.enums.RoleName;
import com.poly.easylearning.repo.IRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepo IRoleRepo;

    public RoleApp create(RoleApp role){
        if(findRole(role.getName()).isPresent()){
            throw new RuntimeException("Role already exist!");
        }
        return IRoleRepo.save(role);
    }

    public Optional<RoleApp> findRole(RoleName roleName){
        return IRoleRepo.findByName(roleName);
    }

    public RoleApp getRoleByName(RoleName roleName) {
        RoleApp roleApp = IRoleRepo.findByName(roleName)
                .orElse(IRoleRepo.findByName(RoleName.ROLE_USER)
                        .orElseGet(() -> {
                            RoleApp newRole = new RoleApp();
                            newRole.setName(RoleName.ROLE_USER);
                            return IRoleRepo.save(newRole);
                        }));
        return roleApp;
    }
}
