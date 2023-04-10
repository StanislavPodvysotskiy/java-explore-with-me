package ru.practicum.ewm.hit.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.HitDto;
import ru.practicum.ewm.hit.EndpointHitMapper;
import ru.practicum.ewm.hit.HitService;
import ru.practicum.ewm.hit.dao.HitRepository;
import ru.practicum.ewm.hit.model.EndpointHit;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Override
    @Transactional
    public HitDto save(HitDto hitDto) {
        EndpointHit endpointHit = EndpointHitMapper.makeEndpointHit(hitDto);
        return EndpointHitMapper.makeHitDto(hitRepository.save(endpointHit));
    }
}
