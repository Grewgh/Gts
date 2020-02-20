package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "street")
@NoArgsConstructor
@Data
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public int getRegion() {
        return region.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_region")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "street", cascade = CascadeType.ALL)
    private List<House> house;

    public String toString() {
        return "Street {id = " + id +
                ", name = '" + name +
                ", id_region = " + region.getId() + "}";
    }

}
