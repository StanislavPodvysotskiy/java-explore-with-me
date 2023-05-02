package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.responseDto.CompilationDto;
import ru.practicum.ewm.dto.responseDto.EventShortDto;
import ru.practicum.ewm.model.Compilation;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation makeCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.isPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static CompilationDto makeCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        List<EventShortDto> events = EventMapper.makeListEventShortDto(List.copyOf(compilation.getEvents()));
        compilationDto.setEvents(events);
        return  compilationDto;
    }

    public static List<CompilationDto> makeListCompilationDto(List<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::makeCompilationDto).collect(Collectors.toList());
    }
}
