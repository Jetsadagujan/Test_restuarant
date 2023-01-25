package th.co.priorsolution.traning.newhttp.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantModel {
    private Integer billNo;

    private Integer tableNumber;

    private String createdBy;

    private String updatedBy;

    private String cookingBy;

    private LocalDateTime billDate;

    private String status;

}
