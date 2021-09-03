package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.model.WorkSchedule;

@Mapper
public interface WorkScheduleMapper {
    
    WorkScheduleMapper INSTANCE = Mappers.getMapper(WorkScheduleMapper.class);

    WorkSchedule toModel(WorkScheduleDTO workScheduleDTO);

    WorkScheduleDTO toDTO(WorkSchedule workSchedule);
}
