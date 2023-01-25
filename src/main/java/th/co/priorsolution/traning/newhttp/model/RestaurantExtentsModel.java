package th.co.priorsolution.traning.newhttp.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantExtentsModel extends RestaurantModel {

    private List<String> orderList = new ArrayList<>();

}
