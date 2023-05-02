package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CompilationRepository;
import ru.practicum.ewm.dto.responseDto.CompilationDto;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.service.PublicCompilationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> findAll(Integer from, Integer size) {
        return CompilationMapper.makeListCompilationDto(
                compilationRepository.findAll(PageRequest.of(from, size)).getContent());
    }

    @Override
    public List<CompilationDto> findAllCompilations(Boolean pinned, Integer from, Integer size) {
        return CompilationMapper.makeListCompilationDto(
                compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size)).getContent());
    }

    @Override
    public CompilationDto findCompilation(Integer complId) {
        return CompilationMapper.makeCompilationDto(compilationRepository.findById(complId)
                .orElseThrow(() -> new NotFoundException("Compilation", complId)));
    }
}
