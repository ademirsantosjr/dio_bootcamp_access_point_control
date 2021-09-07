package one.dio.accesspointcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.dio.accesspointcontrol.model.DateFormat;

@Repository
public interface DateFormatRepository extends JpaRepository<DateFormat, Long>{
    
}
