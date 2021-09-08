package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.DepartmentDTO;
import one.dio.accesspointcontrol.exception.DepartmentNotFoundException;
import one.dio.accesspointcontrol.mapper.DepartmentMapper;
import one.dio.accesspointcontrol.model.Department;
import one.dio.accesspointcontrol.repository.DepartmentRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentService {
    
    DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;

    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department dateFormatToCreate = departmentMapper.toModel(departmentDTO);
        Department savedDepartment = departmentRepository.save(dateFormatToCreate);
        return departmentMapper.toDTO(savedDepartment);
    }

    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll()
                                    .stream()
                                    .map(departmentMapper::toDTO)
                                    .collect(Collectors.toList());        
    }

    public DepartmentDTO findById(long id) throws DepartmentNotFoundException {
        Department foundDepartment = departmentRepository.findById(id)
                                                         .orElseThrow(
                                                             () -> new DepartmentNotFoundException(id));

        return departmentMapper.toDTO(foundDepartment);
    }

    public DepartmentDTO update(DepartmentDTO departmentDTO) throws DepartmentNotFoundException {
        verifyIfDepartmentExists(departmentDTO.getId());

        Department dateFormatToUpdate = departmentMapper.toModel(departmentDTO);

        Department updatedDepartment = departmentRepository.save(dateFormatToUpdate);

        return departmentMapper.toDTO(updatedDepartment);
    }

    public void deleteById(long id) throws DepartmentNotFoundException {
        verifyIfDepartmentExists(id);

        departmentRepository.deleteById(id);
    }

    private Department verifyIfDepartmentExists(long id) throws DepartmentNotFoundException {
        return departmentRepository.findById(id)
                                   .orElseThrow(
                                    () -> new DepartmentNotFoundException(id));
    }
}
