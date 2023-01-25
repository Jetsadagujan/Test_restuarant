package th.co.priorsolution.traning.newhttp.repository;

import th.co.priorsolution.traning.newhttp.model.EmployeesCriteriaModel;
import th.co.priorsolution.traning.newhttp.model.EmployeesModel;

import java.util.List;

public interface EmployeesNativeRepository {

    public List<EmployeesModel> findEmployeesByEmployee(EmployeesCriteriaModel employeesModel);

    public int insertEmployee(List<EmployeesModel> employeesModels);

    public int updateEmployeeById(EmployeesModel employeesModel);

}
