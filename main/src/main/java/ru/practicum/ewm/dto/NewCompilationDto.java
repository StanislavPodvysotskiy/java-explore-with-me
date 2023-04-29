package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewCompilationDto {

    private Boolean pinned;
    @NotBlank
    private String title;
    @NotNull
    private List<Integer> events;
}
