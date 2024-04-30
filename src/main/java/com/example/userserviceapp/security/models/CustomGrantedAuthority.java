package com.example.userserviceapp.security.models;

import com.example.userserviceapp.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;
    public CustomGrantedAuthority(Role role){
        this.authority = role.getRole();
    }
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
