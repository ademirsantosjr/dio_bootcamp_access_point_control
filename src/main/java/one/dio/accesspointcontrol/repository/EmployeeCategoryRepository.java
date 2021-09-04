package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.EmployeeCategory;

@Repository
public interface EmployeeCategoryRepository extends JpaRepository<EmployeeCategory, Long>{
    
}
