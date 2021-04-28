package com.sangria.profile.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * profile pojo
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {

    private String name;
    private String role;

    public Profile(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
