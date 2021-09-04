package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
    
}
