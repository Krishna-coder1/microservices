package org.heapminds.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be blank")
    @Email(message= "Username must be a valid email address")
    private String username;

     @NotNull(message = "Password is required")
     @NotEmpty(message = "Password cannot be blank")
     @Size(min = 10, message = "Password is too short")
    private String password;
}