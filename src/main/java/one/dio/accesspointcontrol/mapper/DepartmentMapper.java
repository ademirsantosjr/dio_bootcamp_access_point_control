package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.model.Department;

@Mapper
public interface DepartmentMapper {
    
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department toModel(DepartmentDTO departmentDTO);

    DepartmentDTO toDTO(Department department);
}
