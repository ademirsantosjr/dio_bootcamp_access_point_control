package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>{
    
}
