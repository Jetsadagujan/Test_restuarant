package th.co.priorsolution.traning.newhttp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class EmployeesEntity {
    @Id

    private int empNo;

    private LocalDate birthDate;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate hireDate;

}
