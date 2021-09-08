package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
