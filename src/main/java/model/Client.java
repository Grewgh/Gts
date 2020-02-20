package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "client")
@NoArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_exemption")
    private Exemption exemption;

    public Exemption getEx(){
        return exemption;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Connection> connection;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Queue> queue;

    public int getExemption() {
        if(exemption!=null) {
            return exemption.getId();
        }
        return 0;
    }

    public String toString() {
        return "Client {id = " + id +
                ", surname = '" + surname +
                ", name = '" + name +
                ", middlename = '" + middlename +
                ", gender = '" + gender +
                ", age = '" + age +
                ", id_exemption = '" + exemption.getId() + "}";
    }

}
