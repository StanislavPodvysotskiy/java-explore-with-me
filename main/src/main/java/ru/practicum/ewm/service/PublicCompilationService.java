package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.responseDto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> findAllCompilations(Boolean printed, Integer from, Integer size);

    CompilationDto findCompilation(Integer compilationId);
}
