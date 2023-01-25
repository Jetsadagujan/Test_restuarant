package th.co.priorsolution.traning.newhttp.service;

import org.springframework.stereotype.Service;
import th.co.priorsolution.traning.newhttp.components.EmployeesTransformComponent;
import th.co.priorsolution.traning.newhttp.entity.EmployeesEntity;
import th.co.priorsolution.traning.newhttp.model.EmployeesCriteriaModel;
import th.co.priorsolution.traning.newhttp.model.EmployeesModel;
import th.co.priorsolution.traning.newhttp.model.ResponseModel;
import th.co.priorsolution.traning.newhttp.repository.EmployeesNativeRepository;
import th.co.priorsolution.traning.newhttp.repository.EmployeesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {
    public EmployeesService(EmployeesRepository employeesRepository,EmployeesTransformComponent employeeTransformComponent,EmployeesNativeRepository employeesNativeRepository) {
        this.employeesRepository = employeesRepository;
        this.employeeTransformComponent = employeeTransformComponent;
        this.employeesNativeRepository = employeesNativeRepository;
    }

    private  EmployeesRepository employeesRepository;
    private EmployeesTransformComponent employeeTransformComponent;
    private EmployeesNativeRepository employeesNativeRepository;

    public ResponseModel<List<EmployeesModel>> getEmployeeByEmployee(EmployeesCriteriaModel employeesModel) {
        ResponseModel<List<EmployeesModel>> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            // do some business
            List<EmployeesModel> transformedData = this.employeesNativeRepository.findEmployeesByEmployee(employeesModel);
            result.setData(transformedData);

        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }


    public ResponseModel<List<EmployeesEntity>> getEmployeesByLastNameRes (String lastName) {
        ResponseModel<List<EmployeesEntity>> result = new ResponseModel<>();
        result.setStatus(200);
        result.setDescription("OK");
        try {
            result.setData((this.getEmployeesLastName(lastName)));


        }catch (Exception e) {
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    private List<EmployeesEntity> getEmployeesLastName(String lastName) {
        return this.employeesRepository.findByLastName(lastName);
    }

    public ResponseModel<Void> insertAndUpdateEmployee(EmployeesModel employeeModel){
        ResponseModel<Void> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            // do some business
            EmployeesEntity employeesEntity = this.employeeTransformComponent.transformModelToEntity(employeeModel);
            this.employeesRepository.save(employeesEntity);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Void> deleteEmployee(EmployeesModel employeeModel){
        ResponseModel<Void> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            // do some business
            EmployeesEntity employeesEntity = new EmployeesEntity();
            this.employeeTransformComponent.transformModelToEntityForUpdate(employeesEntity, employeeModel);
            this.employeesRepository.delete(employeesEntity);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Void> updateEmployee(EmployeesModel employeeModel){
        ResponseModel<Void> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            // do some business
            Optional<EmployeesEntity> employeesEntityOptional = this.employeesRepository.findById(employeeModel.getEmpNo());
            if(employeesEntityOptional.isPresent()){
                EmployeesEntity employeesEntity = employeesEntityOptional.get();
                this.employeeTransformComponent.transformModelToEntityForUpdate(employeesEntity, employeeModel);
                this.employeesRepository.save(employeesEntity);
            } else {
                result.setStatus(404);
                result.setDescription("data to update notfound");
            }

        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Integer> insertEmployeeByNativeSql(List<EmployeesModel> employeeModel){
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            int insertRows = this.employeesNativeRepository.insertEmployee(employeeModel);
            result.setData(insertRows);

        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<Integer> updateEmployeeByNativeSql(EmployeesModel employeeModel){
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            int insertRows = this.employeesNativeRepository.updateEmployeeById(employeeModel);
            result.setData(insertRows);
            result.setDescription("SUCCESS");

        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }
}
