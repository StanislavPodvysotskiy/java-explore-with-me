package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CompilationRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.responseDto.CompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.service.AdminCompilationService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto save(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.makeCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllIds(newCompilationDto.getEvents());
            Set<Event> eventSet = new HashSet<>(events);
            compilation.setEvents(eventSet);
        } else {
            compilation.setEvents(Collections.emptySet());
        }
        return CompilationMapper.makeCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public void delete(Integer compId) {
        Compilation compilation = compilationRepository
                .findById(compId).orElseThrow(() -> new NotFoundException("Compilation", compId));
        compilationRepository.delete(compilation);
    }

    @Override
    @Transactional
    public CompilationDto update(Integer compId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilationRepository
                .findById(compId).orElseThrow(() -> new NotFoundException("Compilation", compId));
        if (updateCompilation.getEvents() != null && !updateCompilation.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllIds(updateCompilation.getEvents());
            Set<Event> eventSet = new HashSet<>(events);
            compilation.setEvents(eventSet);
        }
        if (updateCompilation.getPinned() != null) {
            compilation.setPinned(updateCompilation.getPinned());
        }
        if (updateCompilation.getTitle() != null && !updateCompilation.getTitle().isBlank()) {
            compilation.setTitle(updateCompilation.getTitle());
        }
        return CompilationMapper.makeCompilationDto(compilation);
    }
}
