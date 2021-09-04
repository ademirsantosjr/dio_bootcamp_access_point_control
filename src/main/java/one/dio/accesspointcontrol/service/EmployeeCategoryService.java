package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.EmployeeCategoryDTO;
import one.dio.accesspointcontrol.exception.EmployeeCategoryNotFoundException;
import one.dio.accesspointcontrol.mapper.EmployeeCategoryMapper;
import one.dio.accesspointcontrol.model.EmployeeCategory;
import one.dio.accesspointcontrol.repository.EmployeeCategoryRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeCategoryService {
    
    EmployeeCategoryRepository employeeCategoryRepository;

    private final EmployeeCategoryMapper employeeCategoryMapper = EmployeeCategoryMapper.INSTANCE;

    public EmployeeCategoryDTO create(EmployeeCategoryDTO employeeCategoryDTO) {
        EmployeeCategory employeeCategoryToCreate = employeeCategoryMapper.toModel(employeeCategoryDTO);
        EmployeeCategory savedEmployeeCategory = employeeCategoryRepository.save(employeeCategoryToCreate);
        return employeeCategoryMapper.toDTO(savedEmployeeCategory);
    }

    public List<EmployeeCategoryDTO> findAll() {
        return employeeCategoryRepository.findAll()
                                     .stream()
                                     .map(employeeCategoryMapper::toDTO)
                                     .collect(Collectors.toList());        
    }

    public EmployeeCategoryDTO findById(long id) throws EmployeeCategoryNotFoundException {
        EmployeeCategory foundEmployeeCategory = employeeCategoryRepository.findById(id)
                                                               .orElseThrow(
                                                                   () -> new EmployeeCategoryNotFoundException(id));

        return employeeCategoryMapper.toDTO(foundEmployeeCategory);
    }

    public EmployeeCategoryDTO update(EmployeeCategoryDTO employeeCategoryDTO) throws EmployeeCategoryNotFoundException {
        verifyIfEmployeeCategoryExists(employeeCategoryDTO.getId());

        EmployeeCategory employeeCategoryToUpdate = employeeCategoryMapper.toModel(employeeCategoryDTO);

        EmployeeCategory updatedEmployeeCategory = employeeCategoryRepository.save(employeeCategoryToUpdate);

        return employeeCategoryMapper.toDTO(updatedEmployeeCategory);
    }

    public void deleteById(long id) throws EmployeeCategoryNotFoundException {
        verifyIfEmployeeCategoryExists(id);

        employeeCategoryRepository.deleteById(id);
    }

    private EmployeeCategory verifyIfEmployeeCategoryExists(long id) throws EmployeeCategoryNotFoundException {
        return employeeCategoryRepository.findById(id)
                                         .orElseThrow(
                                             () -> new EmployeeCategoryNotFoundException(id));
    }
}
