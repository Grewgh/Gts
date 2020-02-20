package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "exemption")
@NoArgsConstructor
@Data
public class Exemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "percent")
    private int percent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exemption", cascade = CascadeType.ALL)
    private List<Client> client;

    public String toString() {
        return "Exemption {id = " + id +
                ", name = '" + name +
                ", percent = '" + percent + "}";
    }

}