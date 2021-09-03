package one.dio.accesspointcontrol.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.dio.accesspointcontrol.dto.WorkScheduleDTO;
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

    /*public List<WorkSchedule> findAll() {
        return workDayRepository.findAll();
    }

    public Optional<WorkSchedule> findById(long id) {
        return workDayRepository.findById(id);
    }

    public WorkSchedule update(WorkSchedule workDay) {
        return workDayRepository.save(workDay);
    }

    public void deleteById(long id) {
        workDayRepository.deleteById(id);
    }*/
}
