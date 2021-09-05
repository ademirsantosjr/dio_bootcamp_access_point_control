package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.CompanyDTO;
import one.dio.accesspointcontrol.exception.CompanyNotFoundException;
import one.dio.accesspointcontrol.mapper.CompanyMapper;
import one.dio.accesspointcontrol.model.Company;
import one.dio.accesspointcontrol.repository.CompanyRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyService {
    
    CompanyRepository companyRepository;

    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public CompanyDTO create(CompanyDTO companyDTO) {
        Company companyToCreate = companyMapper.toModel(companyDTO);
        Company savedCompany = companyRepository.save(companyToCreate);
        return companyMapper.toDTO(savedCompany);
    }

    public List<CompanyDTO> findAll() {
        return companyRepository.findAll()
                                .stream()
                                .map(companyMapper::toDTO)
                                .collect(Collectors.toList());        
    }

    public CompanyDTO findById(long id) throws CompanyNotFoundException {
        Company foundCompany = companyRepository.findById(id)
                                          .orElseThrow(
                                              () -> new CompanyNotFoundException(id));

        return companyMapper.toDTO(foundCompany);
    }

    public CompanyDTO update(CompanyDTO companyDTO) throws CompanyNotFoundException {
        verifyIfCompanyExists(companyDTO.getId());

        Company companyToUpdate = companyMapper.toModel(companyDTO);

        Company updatedCompany = companyRepository.save(companyToUpdate);

        return companyMapper.toDTO(updatedCompany);
    }

    public void deleteById(long id) throws CompanyNotFoundException {
        verifyIfCompanyExists(id);

        companyRepository.deleteById(id);
    }

    private Company verifyIfCompanyExists(long id) throws CompanyNotFoundException {
        return companyRepository.findById(id)
                              .orElseThrow(
                                  () -> new CompanyNotFoundException(id));
    }
}
