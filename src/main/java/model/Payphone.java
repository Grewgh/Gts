package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "payphone")
@NoArgsConstructor
@Data
public class Payphone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_house")
    private House house;

    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_telephone_number", referencedColumnName = "id")
    private Telephone_number telephone_number;

    public int getHouse() {
        return house.getId();
    }

    public int getTelephone_number() {
        return telephone_number.getId();
    }

    public String toString() {
        return "Payphone {id = " + id +
                ", id_house = '" + house.getId() +
                ", id_telephone_number = " + telephone_number.getId() + "}";
    }

}
