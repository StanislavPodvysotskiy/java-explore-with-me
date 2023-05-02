package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewUserRequest {

    @NotEmpty
    @Email
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 50)
    private String name;
}
