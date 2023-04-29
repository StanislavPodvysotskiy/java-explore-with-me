package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.dto.responseDto.ParticipationRequestDto;
import ru.practicum.ewm.model.Participation;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationMapper {

    public static ParticipationRequestDto makeParticipationDto(Participation participation) {
        ParticipationRequestDto participationDto = new ParticipationRequestDto();
        participationDto.setId(participation.getId());
        participationDto.setCreated(participation.getCreated());
        participationDto.setEvent(participation.getEvent().getId());
        participationDto.setRequester(participation.getRequester().getId());
        participationDto.setStatus(participation.getStatus().toString());
        return participationDto;
    }

    public static List<ParticipationRequestDto> makeListParticipationDto(List<Participation> participationList) {
        return participationList.stream().map(ParticipationMapper::makeParticipationDto).collect(Collectors.toList());
    }
}
