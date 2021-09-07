package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.CalendarDTO;
import one.dio.accesspointcontrol.model.Calendar;

@Mapper
public interface CalendarMapper {
    
    CalendarMapper INSTANCE = Mappers.getMapper(CalendarMapper.class);

    Calendar toModel(CalendarDTO accessLevelDTO);

    CalendarDTO toDTO(Calendar accessLevel);
}
