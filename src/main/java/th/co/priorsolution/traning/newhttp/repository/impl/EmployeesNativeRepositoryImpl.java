package th.co.priorsolution.traning.newhttp.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.traning.newhttp.model.EmployeesCriteriaModel;
import th.co.priorsolution.traning.newhttp.model.EmployeesModel;
import th.co.priorsolution.traning.newhttp.repository.EmployeesNativeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Repository
public class EmployeesNativeRepositoryImpl implements EmployeesNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public EmployeesNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EmployeesModel> findEmployeesByEmployee(EmployeesCriteriaModel employeesModel) {

        List<Object> paramList = new ArrayList<>();

        String sql = "select emp_no, birth_date, first_name, last_name, gender, hire_date ";
        sql += "from employees ";
        sql += "where 1=1 ";
        if(null != employeesModel.getEmpNo()){
            sql += " and emp_no = ? ";
            paramList.add(employeesModel.getEmpNo());
        }
        if(!StringUtils.isEmpty(employeesModel.getFirstName())){
            sql += " and first_name = ? ";
            paramList.add(employeesModel.getFirstName());
        }
        if(!StringUtils.isEmpty(employeesModel.getLastName())){
            sql += " and last_name = ? ";
            paramList.add(employeesModel.getLastName());
        }
        if(!StringUtils.isEmpty(employeesModel.getGender())){
            sql += " and gender = ? ";
            paramList.add(employeesModel.getGender());
        }
        if(null != employeesModel.getBirthDate1() && null == employeesModel.getBirthDate2()) {
            sql += " and birth_date = ? ";
            paramList.add(employeesModel.getBirthDate1());
        }
        if(null != employeesModel.getBirthDate1() && null != employeesModel.getBirthDate2()) {
            sql += " and birth_date betwwen ? and ? ";
            paramList.add(employeesModel.getBirthDate1());
            paramList.add(employeesModel.getBirthDate2());
        }

        List<EmployeesModel> result = this.jdbcTemplate.query(sql, new RowMapper<EmployeesModel>() {
            @Override
            public EmployeesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                EmployeesModel x =new EmployeesModel();

                int col = 1;
                x.setEmpNo(rs.getInt(col++));
                x.setBirthDate(rs.getDate(col++).toLocalDate());
                x.setFirstName(rs.getString(col++));
                x.setLastName(rs.getString(col++));
                x.setGender(rs.getString(col++));
                x.setHireDate(rs.getDate(col++).toLocalDate());

                return x;
            }
        }, paramList.toArray());
        return result;
    }

    @Override
    public int insertEmployee(List<EmployeesModel> employeesModels) {
        List<Object> paramList = new ArrayList<>();
        String sql = " insert into employees (emp_no, birth_date, first_name, last_name, gender, hire_date) ";
        sql += " values ";
        StringJoiner stringJoiner = new StringJoiner(",");
        for(EmployeesModel e: employeesModels) {
            String value = " ((select max(emp_no)+1 from employees e) , ?, ?, ?, ?, ?) ";
            paramList.add(e.getBirthDate());
            paramList.add(e.getFirstName());
            paramList.add(e.getLastName());
            paramList.add(e.getGender());
            paramList.add(e.getHireDate());
            stringJoiner.add(value);
        }
        sql += stringJoiner.toString();
        int insertedRow = this.jdbcTemplate.update(sql,paramList.toArray());
        return insertedRow;
    }

    @Override
    public int updateEmployeeById(EmployeesModel employeesModel) {
        List<Object> paramList = new ArrayList<>();
        String sql = " UPDATE employees e set ";
        StringJoiner stringJoiner = new StringJoiner(",");
        if(!StringUtils.isEmpty(employeesModel.getFirstName())) {
            stringJoiner.add(" e.first_name = ? ");
            paramList.add(employeesModel.getFirstName());
        }
        sql += stringJoiner.toString();
        if(null != employeesModel.getEmpNo()) {
            sql += " where e.emp_no = ? ";
            paramList.add(employeesModel.getEmpNo());
        }

        int updateRow = this.jdbcTemplate.update(sql,paramList.toArray());
        return updateRow;
    }
}
