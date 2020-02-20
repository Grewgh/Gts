package model;

import lombok.Data;

import java.util.Date;

@Data
public class Request1 {
    private Integer request1IdAts;

    private String request1Surname, request1Name, request1Middlename;

    public Request1(Integer request1IdAts, String request1Surname,String request1Name, String request1Middlename) {
        this.request1IdAts = request1IdAts;
        this.request1Surname = request1Surname;
        this.request1Name = request1Name;
        this.request1Middlename = request1Middlename;
    }
}
