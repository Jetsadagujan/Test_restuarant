package th.co.priorsolution.traning.newhttp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.traning.newhttp.entity.EmployeesEntity;

import java.util.List;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<EmployeesEntity,Integer>, JpaRepository<EmployeesEntity,Integer> {

    List<EmployeesEntity> findByLastName(String lastName);
}
