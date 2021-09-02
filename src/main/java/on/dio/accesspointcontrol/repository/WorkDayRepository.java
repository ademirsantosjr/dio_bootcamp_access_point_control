package on.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import on.dio.accesspointcontrol.model.WorkDay;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long>{
    
}
