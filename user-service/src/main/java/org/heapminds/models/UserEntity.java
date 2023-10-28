package org.heapminds.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="users",indexes=@Index(name="user_index",columnList="user_name"))
@Data
@Entity
public class UserEntity extends AuditEntity {
 
     @NotNull(message = "Username is required")
     @NotEmpty(message = "Username cannot be blank")
     @Column(unique = true, nullable = false, name="user_name")
     private String username;
 
     @NotNull(message = "Password is required")
     @NotBlank(message = "Password cannot be blank")
     private String password;
 
     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(
         name = "user_roles",
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "role_id")
     )
     private List<Role> roles = new ArrayList<>();

}
