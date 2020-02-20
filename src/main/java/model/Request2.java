package model;

import lombok.Data;

@Data
public class Request2 {
    private Integer Number;

    public Request2( Integer Number) {
        this.Number = Number;
    }
}
