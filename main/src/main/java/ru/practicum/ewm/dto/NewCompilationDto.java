package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewCompilationDto {

    private boolean pinned;
    @NotBlank
    @Size(max = 120)
    private String title;
    private List<Integer> events;
}
