package th.co.priorsolution.traning.newhttp.controller.rest;

import org.springframework.web.bind.annotation.*;
import th.co.priorsolution.traning.newhttp.entity.EmployeesEntity;
import th.co.priorsolution.traning.newhttp.model.EmployeesCriteriaModel;
import th.co.priorsolution.traning.newhttp.model.EmployeesModel;
import th.co.priorsolution.traning.newhttp.model.ResponseModel;
import th.co.priorsolution.traning.newhttp.service.EmployeesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeesRestController {
    private EmployeesService employeesService;

    public EmployeesRestController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/employees")
    public ResponseModel<List<EmployeesEntity>> getEmployeesByLastName(@RequestParam(name = "lastName") String lastName){
        return this.employeesService.getEmployeesByLastNameRes(lastName);
    }


    @GetMapping("/employees/{lastName}")
    public ResponseModel<List<EmployeesEntity>> getEmployeesByLastName2(@PathVariable(name = "lastName") String lastName){
        return this.employeesService.getEmployeesByLastNameRes(lastName);
    }

    @PostMapping("/employee")
    public ResponseModel<Void> insertEmployee(
            @RequestBody EmployeesModel employeeModel
    ){
        return this.employeesService.insertAndUpdateEmployee(employeeModel);
    }

    @DeleteMapping("/employee")
    public ResponseModel<Void> deleteEmployee(
            @RequestBody EmployeesModel employeeModel
    ){
        return this.employeesService.deleteEmployee(employeeModel);
    }

    @PostMapping("/find/employee")
    public ResponseModel<List<EmployeesModel>> getEmployeeByLastName(
            @RequestBody EmployeesCriteriaModel employeeModel
    ){
        return this.employeesService.getEmployeeByEmployee(employeeModel);
    }

    @PostMapping("/insert/employee")
    public ResponseModel<Integer> getEmployeeByLastName(
            @RequestBody List<EmployeesModel> employeeModel
    ){
        return this.employeesService.insertEmployeeByNativeSql(employeeModel);
    }

    @PostMapping("/update/employee")
    public ResponseModel<Integer> getEmployeeByLastName(
            @RequestBody EmployeesModel employeeModel
    ){
        return this.employeesService.updateEmployeeByNativeSql(employeeModel);
    }


}
