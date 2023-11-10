package com.productservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordRequest {

    @NotNull(message = "Password must be present for a change password operation")
    @Size(min = 8, max = 100, message = "Password length is too short, minimum of eight (8) characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{8,}$",
            message = "Password must contain at least a number, uppercase and lower case, special character and minimum of 8 character")
    private String oldPassword;
    @NotNull(message = "Password must be present for a change password operation")
    @Size(min = 8, max = 100, message = "Password length is too short, minimum of eight (8) characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-.]).{8,}$",
            message = "Password must contain at least a number, uppercase and lower case, special character and minimum of 8 character")
    private String newPassword;

    @Column(name = "user_name")
    private String userName;


}