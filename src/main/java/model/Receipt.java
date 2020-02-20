package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "receipt")
@NoArgsConstructor
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_con")
    private Connection connection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<Rec_Call> rec_call;

    public int getConnection() {
        return connection.getId();
    }

    @Column(name = "accrual_date")
    private Date accrual_date;

    @Column(name = "sum")
    private float sum;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "payment_date")
    private Date payment_date;

    public Date getPayment_date() {
        if(payment_date!=null) {
            return payment_date;
        }
        return new Date();
    }

    public String toString() {
        return "Receipt {id = " + id +
                ", id_con = '" + connection.getId() +
                ", accrual_date = '" + accrual_date +
                ", sum = '" + sum +
                ", status = '" + status +
                ", payment_date = '" + payment_date + "}";
    }

}
