package th.co.priorsolution.traning.newhttp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.priorsolution.traning.newhttp.entity.ResponseMenuModel;
import th.co.priorsolution.traning.newhttp.model.*;
import th.co.priorsolution.traning.newhttp.repository.RestaurantNativeRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RestaurantService {
    public RestaurantService(RestaurantNativeRepository restaurantNativeRepository) {
        this.restaurantNativeRepository = restaurantNativeRepository;
    }

    private RestaurantNativeRepository restaurantNativeRepository;

    public ResponseModel<Integer> insertNewOrder(RestaurantExtentsModel restaurantModel){
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            this.insertOrder(restaurantModel);
            result.setDescription("SUCCESS");
            result.setStatus(200);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    @Transactional(rollbackFor = SQLException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertOrder(RestaurantExtentsModel restaurantModel){

        int billNo = this.restaurantNativeRepository.insertBill(restaurantModel);
        this.restaurantNativeRepository.insertOrder(billNo , restaurantModel);
    }

    public ResponseModel<List<RestaurantModel>> getOrderByChef(RequestByChefModel requestByChefModel){
        ResponseModel<List<RestaurantModel>> result = new ResponseModel<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
//            this.insertOrder(restaurantModel);
            List<RestaurantModel> dataResponse = this.restaurantNativeRepository.getOrderByChef(requestByChefModel);
            result.setDescription("SUCCESS");
            result.setData(dataResponse);
            result.setStatus(200);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    public ResponseModel<ResponseMenuModel> getOrderDetailById(RequestQueryMenuById requestQueryMenuById) {
        ResponseModel<ResponseMenuModel> result = new ResponseModel();
        List<String> paramList = new ArrayList<>();

        result.setStatus(201);
        result.setDescription("ok");
        try {
            ResponseMenuModel responseMenuModel = new ResponseMenuModel();
            List<ResponseQueryMenuModel> dataFromQuery = this.restaurantNativeRepository.getOrderDetailById(requestQueryMenuById);
            if(dataFromQuery.size() > 0) {
                responseMenuModel.setBillNo(dataFromQuery.get(0).getBillNo());
                responseMenuModel.setTableNumber(dataFromQuery.get(0).getTableNumber());
                responseMenuModel.setCreatedBy(dataFromQuery.get(0).getCreatedBy());
                responseMenuModel.setStatus(dataFromQuery.get(0).getStatus());
                responseMenuModel.setBillDate(dataFromQuery.get(0).getBillDate());
                responseMenuModel.setUpdatedBy(dataFromQuery.get(0).getUpdatedBy());
                responseMenuModel.setCookingBy(dataFromQuery.get(0).getCookingBy());

                for (ResponseQueryMenuModel x : dataFromQuery ){
                    paramList.add(x.getMenu());
                }
                responseMenuModel.setMenus(paramList);
            }
            result.setData(responseMenuModel);
            result.setDescription("SUCCESS");
            result.setStatus(200);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }

        return result;
    }

    public ResponseModel<Integer> updateOrderStatus (RequestUpdateStatus requestUpdateStatus) {
        ResponseModel<Integer> result = new ResponseModel();

        result.setStatus(201);
        result.setDescription("ok");

        try {
            Integer dataResponse = this.restaurantNativeRepository.updateOrderStatus(requestUpdateStatus);
            result.setData(dataResponse);
            result.setDescription("SUCCESS");
            result.setStatus(200);
        } catch (Exception e){
            result.setStatus(500);
            result.setDescription(e.getMessage());
        }
        return  result;
    }
}
