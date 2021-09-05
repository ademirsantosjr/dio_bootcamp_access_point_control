package one.dio.accesspointcontrol.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.dio.accesspointcontrol.dto.CompanyDTO;
import one.dio.accesspointcontrol.model.Company;

@Mapper
public interface CompanyMapper {
    
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toModel(CompanyDTO companyDTO);

    CompanyDTO toDTO(Company company);
}
