package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;
import one.dio.accesspointcontrol.model.EmployeeCategory;

@Mapper
public interface EmployeeCategoryMapper {
    
    EmployeeCategoryMapper INSTANCE = Mappers.getMapper(EmployeeCategoryMapper.class);

    EmployeeCategory toModel(EmployeeCategoryDTO employeeCategoryDTO);

    EmployeeCategoryDTO toDTO(EmployeeCategory employeeCategory);
}
