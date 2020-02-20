package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "region")
@NoArgsConstructor
@Data
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_city_ats")
    private City_ats city_ats;

    public int getCity_ats() {
        return city_ats.getId();
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region", cascade = CascadeType.ALL)
    private List<Street> street;

    public String toString() {
        return "Region {id = " + id +
                ", name = '" + name +
                ", id_city_ats = " + city_ats.getId() + "}";
    }

}
