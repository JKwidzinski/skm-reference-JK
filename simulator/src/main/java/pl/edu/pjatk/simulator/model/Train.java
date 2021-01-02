package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="trains")
public class Train implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="train", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Compartment> compartments;

    @Column(name="current_station")
    private Station currentStation;

    @Column(name="going_to_gdansk")
    private boolean goingToGdansk;

    @Transient
    private int currentPauseTime;

    public Train(List<Compartment> compartments, Station currentStation, boolean goingToGdansk) {
        this.compartments = compartments;
        this.currentStation = currentStation;
        this.goingToGdansk = goingToGdansk;
        this.currentPauseTime = 0;
    }

    public Train() {

    }

    public Collection<Compartment> getCompartments() {
        return compartments;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public boolean isGoingToGdansk() {
        return goingToGdansk;
    }

    public int getCurrentPauseTime() {
        return currentPauseTime;
    }

    public void setCompartments(List<Compartment> compartments) {
        this.compartments = compartments;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public void setGoingToGdansk(boolean goingToGdansk) {
        this.goingToGdansk = goingToGdansk;
    }

    public void setCurrentPauseTime(int currentPauseTime) {
        this.currentPauseTime = currentPauseTime;
    }

    public void move() {
        if (currentPauseTime > 0) {
            currentPauseTime--;
        } else {
            int nextStationModifier = goingToGdansk ? 1 : -1;
            currentStation = Station.values()[currentStation.ordinal() + nextStationModifier];
            currentPauseTime = currentStation.getPauseTime();

            if (currentStation.getPauseTime() > 0) {
                goingToGdansk = !goingToGdansk;
            }

            compartments.forEach(c -> c.disembark(this.currentStation));
            compartments.forEach(c -> {
                List<Person> people = PersonGenerator.generatePeople(this.currentStation);
                people.forEach(c::embark);
            });
        }
    }

    @Override
    public Long getId() {
        return id;
    }
}
