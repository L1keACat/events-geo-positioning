package com.likeacat.eventsGeoPositioning.model;

import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    @ApiModelProperty(notes = "The database generated user ID")
    @Id
    private String id;
    @ApiModelProperty(notes = "Username of the user")
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String username;
    @ApiModelProperty(notes = "User's password")
    private String password;
    @ApiModelProperty(notes = "Is user enabled in the system")
    private boolean enabled;
    @ApiModelProperty(notes = "Set of users roles")
    @DBRef
    private Set<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getStringRoles() {
        for (Role role : roles) {
            if ("ADMIN".equals(role.getRole()))
                return "ADMIN";
        }
        return "USER";
    }
}