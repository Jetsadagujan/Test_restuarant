package th.co.priorsolution.traning.newhttp.repository;

import th.co.priorsolution.traning.newhttp.model.*;

import java.util.List;

public interface RestaurantNativeRepository {
    int insertBill(RestaurantModel restaurantModel);

    void insertOrder(Integer billNo , RestaurantExtentsModel restaurantModel);

    List<RestaurantModel> getOrderByChef(RequestByChefModel requestByChefModel);

    List<ResponseQueryMenuModel> getOrderDetailById (RequestQueryMenuById requestQueryMenuById);

    int updateOrderStatus(RequestUpdateStatus requestUpdateStatus);

}
