package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "city_ats")
public class City_ats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ats", referencedColumnName = "id")
    private Ats ats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city_ats", cascade = CascadeType.ALL)
    private List<Region> region;

    @OneToMany(orphanRemoval=true, fetch = FetchType.LAZY, mappedBy = "city_ats", cascade = CascadeType.DETACH)
    private List<Telephone_number> telephone_number;

    public int getAts() {
        return ats.getId();
    }

}
