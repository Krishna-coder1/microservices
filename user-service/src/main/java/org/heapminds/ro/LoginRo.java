package org.heapminds.ro;

import java.util.List;

import org.heapminds.models.Role;

import lombok.Data;

@Data
public class LoginRo  {
    private String authToken;
    private  List<Role> roles;

}
