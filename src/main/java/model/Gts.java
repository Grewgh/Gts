package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "gts")
@NoArgsConstructor
@Data
public class Gts {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gts", cascade = CascadeType.ALL)
    private List<Ats> ats;

    public Gts(Integer id, String name)
    {
        super();
        this.id    = id;
        this.name  = name;
    }

    public String toString() {
        String atss = "";
        if ((ats != null) && (ats.size() > 0)) {
            for (int i = 0; i < ats.size(); i++) {
                if (i > 0)
                    atss += ",";
                atss += ats.get(i).toString();
            }
        }
        return "User {id = " + String.valueOf(id) +
                ", name = '" + name + "', ats =[" + atss + "]}";
    }

}
