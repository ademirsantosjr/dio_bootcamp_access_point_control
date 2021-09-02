package on.dio.accesspointcontrol.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import on.dio.accesspointcontrol.model.WorkDay;
import on.dio.accesspointcontrol.repository.WorkDayRepository;

@Service
public class WorkDayService {
    
    @Autowired
    WorkDayRepository workDayRepository;

    public WorkDay save(WorkDay workDay) {
        return workDayRepository.save(workDay);
    }

    public List<WorkDay> findAll() {
        return workDayRepository.findAll();
    }

    public Optional<WorkDay> findById(long id) {
        return workDayRepository.findById(id);
    }
}
