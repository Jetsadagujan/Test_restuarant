package th.co.priorsolution.traning.newhttp.controller.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.priorsolution.traning.newhttp.entity.ResponseMenuModel;
import th.co.priorsolution.traning.newhttp.model.*;
import th.co.priorsolution.traning.newhttp.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/res")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/insert/oder")
    public ResponseModel<Integer> insertNewOrder(
            @RequestBody RestaurantExtentsModel restaurantModel
    ){
        return this.restaurantService.insertNewOrder(restaurantModel);
    }

    @PostMapping("/get/oder")
    public ResponseModel<List<RestaurantModel>> getOrder(
            @RequestBody RequestByChefModel requestByChefModel
            ){
        return this.restaurantService.getOrderByChef(requestByChefModel);
    }

    @PostMapping("/get/oder/detail")
    public ResponseModel<ResponseMenuModel> getOrderDetailById(
            @RequestBody RequestQueryMenuById requestQueryMenuById
            ){
        return this.restaurantService.getOrderDetailById(requestQueryMenuById);
    }

    @PostMapping("/update/oder/status")
    public ResponseModel<Integer> updateOrderStatus(
            @RequestBody RequestUpdateStatus requestUpdateStatus
    ){
        return this.restaurantService.updateOrderStatus(requestUpdateStatus);
    }

}
