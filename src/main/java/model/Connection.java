package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "connection")
@NoArgsConstructor
@Data
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "disconnection_date")
    private Date disconnection_date;

    @Column(name = "connection_date")
    private Date connection_date;

    public int getApartment() {
        return apartment.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_apartment")
    private Apartment apartment;

    @Column(name = "type_telephone")
    private String type_telephone;

    @Column(name = "status")
    private Boolean status;

    public int getTelephone_number() {
        return telephone_number.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_number")
    private Telephone_number telephone_number;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "connection", cascade = CascadeType.ALL)
    private List<Receipt> receipt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "connection", cascade = CascadeType.ALL)
    private List<Call> call;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private Client client;

    public int getClient() {
        return client.getId();
    }

    public Date getDisconnection_date() {
        if(disconnection_date!=null) {
            return disconnection_date;
        }
        return new Date();
    }

    public String toString() {
        return "Connection {id = " + id +
                ", installation_date = '" + disconnection_date +
                ", connection_date = '" + connection_date +
                ", apartment = " + apartment.getId() +
                ", type_telephone = '" + type_telephone +
                ", status = '" + status +
                ", id_number = '" + telephone_number.getId()+
                ", id_client = '" + client.getId()+ "}";
    }

}
