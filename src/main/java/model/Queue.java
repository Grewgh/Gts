package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "queue")
@NoArgsConstructor
@Data
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "queue_date")
    private Date queue_date;

    public int getClient() {
        return client.getId();
    }

    @Column(name = "exemption")
    private boolean exemption;

    public String toString() {
        return "Queue {id = " + id +
                ", client = '" + client.getId() +
                ", queue_date = '" + queue_date +
                ", exemption = '" + exemption + "}";
    }

}