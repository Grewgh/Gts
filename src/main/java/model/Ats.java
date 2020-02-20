package model;

import HibSession.HibernateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.*;


@Entity
@Table(name = "ats")
@NoArgsConstructor
@Data
public class Ats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "ats", cascade = CascadeType.ALL)
    private City_ats city_ats;

    public int getGts() {
        return gts.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_gts")
    private Gts gts;


    public Ats(String name, String type_ats) {
        super();
        this.name = name;
    }



    public Ats(String name) {
        super();
        this.name = name;
    }


    public String toString() {
        return "Ats {id = " + id +
                ", name = '" + name +
                ", id_gts = " + gts.getId() + "}";
    }

}
