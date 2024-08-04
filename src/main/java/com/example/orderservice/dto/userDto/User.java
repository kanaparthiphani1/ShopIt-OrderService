package com.example.orderservice.dto.userDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Role> roles;
    private boolean isEmailVerified;
}
