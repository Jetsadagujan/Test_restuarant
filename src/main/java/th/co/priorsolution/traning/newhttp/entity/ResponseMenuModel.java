package th.co.priorsolution.traning.newhttp.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseMenuModel {
    private Integer billNo;

    private Integer tableNumber;

    private String createdBy;

    private String updatedBy;

    private String cookingBy;

    private String status;

    private LocalDateTime billDate;

    private List<String> menus;
}
