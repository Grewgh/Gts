package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "house")
@NoArgsConstructor
@Data
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getStreet() {
        return street.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_street")
    private Street street;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    private List<Apartment> apartment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    private List<Payphone> payphone;

    public String toString() {
        return "House {id = " + id +
                ", name = '" + name +
                ", id_street = " + street.getId() + "}";
    }

}
