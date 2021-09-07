package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.DateFormatDTO;
import one.dio.accesspointcontrol.model.DateFormat;

@Mapper
public interface DateFormatMapper {
    
    DateFormatMapper INSTANCE = Mappers.getMapper(DateFormatMapper.class);

    DateFormat toModel(DateFormatDTO accessLevelDTO);

    DateFormatDTO toDTO(DateFormat accessLevel);
}
