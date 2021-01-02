package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="compartments")
public class Compartment implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="capacity")
    private int capacity;

    @OneToMany(mappedBy="compartment", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Person> occupants;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="train_id")
    private Train train;

    public Compartment(int capacity) {
        this.capacity = capacity;
        occupants = new ArrayList<>();
    }

    public Compartment() {

    }

    @Override
    public Long getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public Collection<Person> getOccupants() {
        return occupants;
    }

    public void setOccupants(List<Person> occupants) {
        this.occupants = occupants;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void embark(Person person) {
        if (occupants.size() < capacity) {
            occupants.add(person);
        }
    }

    public void disembark(Station station) {
        List<Person> leaving = occupants.stream()
                .filter(p -> p.getDestination().equals(station))
                .collect(Collectors.toList());

        occupants.removeAll(leaving);
    }

}
