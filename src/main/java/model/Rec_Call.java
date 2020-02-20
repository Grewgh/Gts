package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "receipt_call")
@NoArgsConstructor
@Data
public class Rec_Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_receipt")
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_call")
    private Call call;

    public int getReceipt() {
        return receipt.getId();
    }

    public int getCall() {
        return call.getId();
    }

    public String toString() {
        return "Call {id = " + id +
                ", id_receipt = '" + receipt.getId() +
                ", id_call = '" + call.getId() +"}";
    }

}
