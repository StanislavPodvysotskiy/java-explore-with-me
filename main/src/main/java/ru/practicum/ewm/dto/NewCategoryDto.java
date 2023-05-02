package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewCategoryDto {

    @NotBlank
    @Size(max = 50)
    private String name;
}
