package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.responseDto.CompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;

public interface AdminCompilationService {

    CompilationDto save(NewCompilationDto newCompilationDto);

    void delete(Integer compId);

    CompilationDto update(Integer compId, UpdateCompilationRequest updateCompilation);
}
