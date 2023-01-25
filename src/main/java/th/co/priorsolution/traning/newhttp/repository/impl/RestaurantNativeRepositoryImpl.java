package th.co.priorsolution.traning.newhttp.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import th.co.priorsolution.traning.newhttp.model.*;
import th.co.priorsolution.traning.newhttp.repository.RestaurantNativeRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Repository
public class RestaurantNativeRepositoryImpl implements RestaurantNativeRepository {

    private LocalDateTime date = LocalDateTime.now();
    private JdbcTemplate jdbcTemplate;

    public RestaurantNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertBill(RestaurantModel restaurantModel) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        List<Object> paramList = new ArrayList<>();

        String sql = " insert into bill_management ( table_number, created_by, bill_date, updated_by) ";
        sql += " value ";



        if(restaurantModel.getTableNumber() != null &&  !StringUtils.isEmpty(restaurantModel.getCreatedBy())) {
            sql += " ( ? , ?, ?, ? ) ";
            paramList.add(restaurantModel.getTableNumber());
            paramList.add(restaurantModel.getCreatedBy());
            paramList.add(date);
            paramList.add(restaurantModel.getCreatedBy());
        }
        String finalSql = sql;
        int rowsAffected = this.jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement =  connection.prepareStatement(finalSql, Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < paramList.size(); i++) {
                        int sqlI = i+1;
                        preparedStatement.setObject(sqlI,paramList.get(i));
                    }

                    return preparedStatement;
        },generatedKeyHolder
        );

//        int rowsAffected = this.jdbcTemplate.update(sql,paramList.toArray(),generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();

        log.info("rowsAffected = {}, id={}", rowsAffected,generatedKeyHolder);
        return id;
    }

    @Override
    public void insertOrder(Integer billNo, RestaurantExtentsModel restaurantModel) {
        List<Object> paramList = new ArrayList<>();

        String sql = " insert into order_management (order_id, menu, bill_no) ";
        sql += " values ";
        StringJoiner stringJoiner = new StringJoiner(",");
        if(null != billNo && restaurantModel.getOrderList().size() > 0) {
            for (int i = 0; i < restaurantModel.getOrderList().size(); i++) {
                String query = " ( ?, ?, ? ) " ;
                String orderId =i + "" + billNo;
                paramList.add(orderId);
                paramList.add(restaurantModel.getOrderList().get(i));
                paramList.add(billNo);
                stringJoiner.add(query);
            }
            sql += stringJoiner.toString();
        }
        this.jdbcTemplate.update(sql,paramList.toArray());
    }

    @Override
    public List<RestaurantModel> getOrderByChef(RequestByChefModel requestByChefModel) {
        List<Object> paramList = new ArrayList<>();
        String sql = " select * from bill_management ";
        if(!StringUtils.isEmpty(requestByChefModel.getStatus())) {
            sql += " where and status = ? ";
            paramList.add(requestByChefModel.getStatus());
        }
        sql += " order by bill_no desc ";

        List<RestaurantModel> result = this.jdbcTemplate.query(sql, new RowMapper<RestaurantModel>() {
            @Override
            public RestaurantModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                RestaurantModel x = new RestaurantModel();

                x.setBillNo(rs.getInt("bill_no"));
                x.setTableNumber(rs.getInt("table_number"));
                x.setCreatedBy(rs.getString("created_by"));
                x.setUpdatedBy(rs.getString("updated_by"));
                x.setCookingBy(rs.getString("cooking_by"));
                x.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                x.setStatus(rs.getString("status"));
                return x;
            }
        },paramList.toArray());
        return result;
    }

    @Override
    public  List<ResponseQueryMenuModel> getOrderDetailById(RequestQueryMenuById requestQueryMenuById) {
        List<Object> paramList = new ArrayList<>();
        String sql = "SELECT bm.bill_no,bm.table_number,bm.created_by,bm.status,bm.bill_date,bm.updated_by, bm.cooking_by,om.menu\n" +
                " FROM bill_management bm\n " +
                " LEFT JOIN order_management om\n " +
                " ON bm.bill_no = om.bill_no\n " +
                " where bm.bill_no = ? ";
        paramList.add(requestQueryMenuById.getId());

        List<ResponseQueryMenuModel> result = this.jdbcTemplate.query(sql, new RowMapper<ResponseQueryMenuModel>() {
            @Override
            public ResponseQueryMenuModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                ResponseQueryMenuModel x = new ResponseQueryMenuModel();
                x.setBillNo(rs.getInt("bill_no"));
                x.setTableNumber(rs.getInt("table_number"));
                x.setCreatedBy(rs.getString("created_by"));
                x.setUpdatedBy(rs.getString("updated_by"));
                x.setCookingBy(rs.getString("cooking_by"));
                x.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                x.setStatus(rs.getString("status"));
                x.setMenu(rs.getString("menu"));
                return x;
            }
        },paramList.toArray());

        return result;
    }

    @Override
    public int updateOrderStatus(RequestUpdateStatus requestUpdateStatus) {
        List<Object> paramList =new ArrayList<>();
        String sql = " UPDATE bill_management bm\n" +
                "SET\n" +
                "    bm.status = ?,\n" +
                "    bm.cooking_by = ?,\n" +
                "    bm.updated_by = ?\n" +
                "WHERE bm.bill_no = ?; ";
        paramList.add(requestUpdateStatus.getStatus());
        paramList.add(requestUpdateStatus.getCookingBy());
        if(!StringUtils.isEmpty( requestUpdateStatus.getCookingBy())) {
            paramList.add(requestUpdateStatus.getCookingBy());
        } else if (!StringUtils.isEmpty( requestUpdateStatus.getWaitressBy())) {
            paramList.add(requestUpdateStatus.getWaitressBy());
        }
        paramList.add(requestUpdateStatus.getId());

        int rowsAffected = this.jdbcTemplate.update(sql,paramList.toArray());
        return rowsAffected;
    }
}
