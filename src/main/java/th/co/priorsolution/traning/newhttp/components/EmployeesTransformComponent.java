package th.co.priorsolution.traning.newhttp.components;

import org.springframework.stereotype.Component;
import th.co.priorsolution.traning.newhttp.entity.EmployeesEntity;
import th.co.priorsolution.traning.newhttp.model.EmployeesModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeesTransformComponent {

    public List<EmployeesModel> transformEntityListToModelList(List<EmployeesEntity> employeesEntity){
        List<EmployeesModel> employeeModelList = new ArrayList<>();
        for (EmployeesEntity x : employeesEntity) {
            EmployeesModel y = this.transformEntityToModel(x);
            employeeModelList.add(y);
        }

        return employeeModelList;
    }

    public EmployeesModel transformEntityToModel(EmployeesEntity employeesEntity) {
        EmployeesModel employeesModel = new EmployeesModel();
        employeesModel.setGender(employeesEntity.getGender());
        employeesModel.setEmpNo(employeesEntity.getEmpNo());
        employeesModel.setBirthDate(employeesEntity.getBirthDate());
        employeesModel.setLastName(employeesEntity.getLastName());
        employeesModel.setFirstName(employeesEntity.getFirstName());
        employeesModel.setHireDate(employeesEntity.getHireDate());

        return employeesModel;
    }

    public EmployeesEntity transformModelToEntity(EmployeesModel employeesModel) {
        EmployeesEntity employeesEntity = new EmployeesEntity();
        employeesEntity.setGender(employeesModel.getGender());
        employeesEntity.setBirthDate(employeesModel.getBirthDate());
        employeesEntity.setFirstName(employeesModel.getFirstName());
        employeesEntity.setEmpNo(employeesModel.getEmpNo());
        employeesEntity.setHireDate(employeesModel.getHireDate());
        employeesEntity.setLastName(employeesModel.getLastName());
        return employeesEntity;
    }

    public void transformModelToEntityForUpdate(EmployeesEntity employeesEntity,EmployeesModel employeeModel){
        employeesEntity.setGender(employeeModel.getGender());
        employeesEntity.setBirthDate(employeeModel.getBirthDate());
        employeesEntity.setFirstName(employeeModel.getFirstName());
        employeesEntity.setEmpNo(employeeModel.getEmpNo());
        employeesEntity.setHireDate(employeeModel.getHireDate());
        employeesEntity.setLastName(employeeModel.getLastName());
    }
}
