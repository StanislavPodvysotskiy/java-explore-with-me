package ru.practicum.ewm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.dto.base.UpdateEventRequest;
import ru.practicum.ewm.emun.StateAction;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEventUserRequest extends UpdateEventRequest {

    private StateAction stateAction;
}
