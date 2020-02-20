package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tariff")
@NoArgsConstructor
@Data
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "day_night")
    private String day_night;

    @Column(name = "type")
    private String type;

    @Column(name = "price_minute")
    private float price_minute;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tariff", cascade = CascadeType.ALL)
    private List<Call> call;

    public String toString() {
        return "Tariff {id = " + id +
                ", day_night = '" + day_night +
                ", type = '" + type +
                ", price_minute = '" + price_minute + "}";
    }

}