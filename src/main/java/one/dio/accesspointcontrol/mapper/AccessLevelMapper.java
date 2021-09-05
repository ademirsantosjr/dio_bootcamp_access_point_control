package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.AccessLevelDTO;
import one.dio.accesspointcontrol.model.AccessLevel;

@Mapper
public interface AccessLevelMapper {
    
    AccessLevelMapper INSTANCE = Mappers.getMapper(AccessLevelMapper.class);

    AccessLevel toModel(AccessLevelDTO accessLevelDTO);

    AccessLevelDTO toDTO(AccessLevel accessLevel);
}
