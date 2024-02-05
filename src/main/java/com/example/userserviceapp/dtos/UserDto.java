package com.example.userserviceapp.dtos;

import com.example.userserviceapp.models.Role;
import com.example.userserviceapp.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();
    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.roles = user.getRoles();
        return userDto;
    }
}
