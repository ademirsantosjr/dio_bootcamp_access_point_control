package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
    
}
