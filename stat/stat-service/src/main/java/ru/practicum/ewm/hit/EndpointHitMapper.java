package ru.practicum.ewm.hit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.HitDto;
import ru.practicum.ewm.hit.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointHitMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHit makeEndpointHit(HitDto hitDto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(hitDto.getApp());
        endpointHit.setUri(hitDto.getUri());
        endpointHit.setIp(hitDto.getIp());
        endpointHit.setTimestamp(LocalDateTime.parse(hitDto.getTimestamp(), FORMATTER));
        return endpointHit;
    }

    public static HitDto makeHitDto(EndpointHit endpointHit) {
        HitDto hitDto = new HitDto();
        hitDto.setApp(endpointHit.getApp());
        hitDto.setUri(endpointHit.getUri());
        hitDto.setIp(endpointHit.getIp());
        hitDto.setTimestamp(endpointHit.getTimestamp().format(FORMATTER));
        return hitDto;
    }
}
