package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCompilationRequest {

    private Boolean pinned;
    @Size(max = 120)
    private String title;
    private List<Integer> events;
}
