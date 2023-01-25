package th.co.priorsolution.traning.newhttp.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseQueryMenuModel {
    private Integer billNo;

    private Integer tableNumber;

    private String createdBy;

    private String updatedBy;

    private String cookingBy;

    private String status;

    private LocalDateTime billDate;

    private String menu;
}
