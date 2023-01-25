package th.co.priorsolution.traning.newhttp.model;

import lombok.Data;

@Data
public class ResponseModel<T> {
    private int status;

    private String description;

    private T data;
}
