package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.AccessLevel;

@Repository
public interface AccessLevelRepository extends JpaRepository<AccessLevel, Long>{
    
}
