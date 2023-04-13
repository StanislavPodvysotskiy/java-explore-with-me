package ru.practicum.ewm.hit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.HitDto;
import ru.practicum.ewm.hit.model.EndpointHit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointHitMapper {

    public static EndpointHit makeEndpointHit(HitDto hitDto) {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(hitDto.getApp());
        endpointHit.setUri(hitDto.getUri());
        endpointHit.setIp(hitDto.getIp());
        endpointHit.setTimestamp(hitDto.getTimestamp());
        return endpointHit;
    }

    public static HitDto makeHitDto(EndpointHit endpointHit) {
        HitDto hitDto = new HitDto();
        hitDto.setApp(endpointHit.getApp());
        hitDto.setUri(endpointHit.getUri());
        hitDto.setIp(endpointHit.getIp());
        hitDto.setTimestamp(endpointHit.getTimestamp());
        return hitDto;
    }
}
