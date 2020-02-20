package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "apartment")
@NoArgsConstructor
@Data
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getHouse() {
        return house.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_house")
    private House house;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<Connection> connection;

    public String toString() {
        return "Apartment {id = " + id +
                ", name = '" + name +
                ", id_house = " + house.getId() + "}";
    }

}
