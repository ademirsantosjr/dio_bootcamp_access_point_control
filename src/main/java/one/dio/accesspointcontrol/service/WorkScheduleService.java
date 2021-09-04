package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
import one.dio.accesspointcontrol.exception.WorkScheduleNotFoundException;
import one.dio.accesspointcontrol.mapper.WorkScheduleMapper;
import one.dio.accesspointcontrol.model.WorkSchedule;
import one.dio.accesspointcontrol.repository.WorkScheduleRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WorkScheduleService {
    
    WorkScheduleRepository workScheduleRepository;

    private final WorkScheduleMapper workScheduleMapper = WorkScheduleMapper.INSTANCE;

    public WorkScheduleDTO create(WorkScheduleDTO workScheduleDTO) {
        WorkSchedule workScheduleToCreate = workScheduleMapper.toModel(workScheduleDTO);
        WorkSchedule savedWorkSchedule = workScheduleRepository.save(workScheduleToCreate);
        return workScheduleMapper.toDTO(savedWorkSchedule);
    }

    public List<WorkScheduleDTO> findAll() {
        return workScheduleRepository.findAll()
                                     .stream()
                                     .map(workScheduleMapper::toDTO)
                                     .collect(Collectors.toList());        
    }

    public WorkScheduleDTO findById(long id) throws WorkScheduleNotFoundException {
        WorkSchedule foundWorkSchedule = workScheduleRepository.findById(id)
                                                               .orElseThrow(
                                                                   () -> new WorkScheduleNotFoundException(id));

        return workScheduleMapper.toDTO(foundWorkSchedule);
    }

    public WorkScheduleDTO update(WorkScheduleDTO workScheduleDTO) throws WorkScheduleNotFoundException {
        verifyIfWorkScheduleDTOExists(workScheduleDTO.getId());

        WorkSchedule workScheduleToUpdate = workScheduleMapper.toModel(workScheduleDTO);

        WorkSchedule updatedWorkSchedule = workScheduleRepository.save(workScheduleToUpdate);

        return workScheduleMapper.toDTO(updatedWorkSchedule);
    }

    public void deleteById(long id) throws WorkScheduleNotFoundException {
        verifyIfWorkScheduleDTOExists(id);

        workScheduleRepository.deleteById(id);
    }

    private WorkSchedule verifyIfWorkScheduleDTOExists(long id) throws WorkScheduleNotFoundException {
        return workScheduleRepository.findById(id)
                                     .orElseThrow(
                                         () -> new WorkScheduleNotFoundException(id));
    }
}
