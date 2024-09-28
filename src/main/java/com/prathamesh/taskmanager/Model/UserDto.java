package com.prathamesh.taskmanager.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDto extends Users{
    @Override
    @JsonIgnore
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    @JsonIgnore
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    @JsonIgnore
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    @JsonIgnore
    public void setRole(Role role) {
        super.setRole(role);
    }
}
