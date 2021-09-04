package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.EventDTO;
import one.dio.accesspointcontrol.model.Event;

@Mapper
public interface EventMapper {
    
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toModel(EventDTO eventDTO);

    EventDTO toDTO(Event event);
}
