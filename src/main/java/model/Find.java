package model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Find {
    private Date connection_date;

    private String name;

    private Boolean status;

    private int number;

    private int id_client;

    public Find(Date connection_date, String name, boolean status, int number, int id_client) {
        this.connection_date = connection_date;
        this.name = name;
        this.status = status;
        this.number = number;
        this.id_client = id_client;
    }
}
