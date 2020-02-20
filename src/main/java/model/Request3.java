package model;

import lombok.Data;

@Data
public class Request3 {
    private Integer idPayphone;

    public Request3(Integer idPayphone) {
        this.idPayphone = idPayphone;
    }
}
