package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "call")
@NoArgsConstructor
@Data
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "call", cascade = CascadeType.ALL)
    private List<Rec_Call> rec_call;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_con")
    private Connection connection;

    @Column(name = "call_start")
    private Date call_start;

    @Column(name = "call_stop")
    private Date call_stop;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_tariff")
    private Tariff tariff;

    @Column(name = "call_duration")
    private String call_duration;

    @Column(name = "sum")
    private float sum;

    public int getConnection() {
            return connection.getId();
    }

    public int getTariff() {
        return tariff.getId();
    }

/*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "call", cascade = CascadeType.ALL)
    private List<Receipt_Call> receipt_call;
*/



    public String toString() {
        return "Call {id = " + id +
                ", id_con = '" + connection.getId() +
                ", call_start = '" + call_start +
                ", call_stop = '" + call_stop +
                ", id_tariff = '" + tariff.getId() +
                ", call_duration = '" + call_duration +
                ", sum = '" + sum + "}";
    }

}
