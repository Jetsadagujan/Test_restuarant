package th.co.priorsolution.traning.newhttp.model;

import lombok.Data;

@Data
public class RequestUpdateStatus {
    private Integer id;
    private String status;
    private String cookingBy;
    private String waitressBy;
}
