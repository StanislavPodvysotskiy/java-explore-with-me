package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCompilationRequest {

    private Boolean pinned;
    private String title;
    private List<Integer> events;
}
