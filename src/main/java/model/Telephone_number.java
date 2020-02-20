package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "telephone_number")
@NoArgsConstructor
@Data
public class Telephone_number {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private int number;

    @Column(name = "status")
    private boolean status;

    public int getCity_ats() {
        return city_ats.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_ats")
    private City_ats city_ats;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "telephone_number", cascade = CascadeType.PERSIST)
    private Payphone payphone;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "telephone_number", cascade = CascadeType.ALL)
    private List<Connection> connection;

    public String toString() {
        return "Telephone_number {id = " + id +
                ", number = '" + number +
                ", status = '" + status +
                ", type_ats = '" + city_ats.getId() + "}";
    }

}
