package pl.edu.pjatk.simulator.model;

import pl.edu.pjatk.simulator.service.DbEntity;

import javax.persistence.*;

@Entity
@Table(name="people")
public class Person implements DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="destination")
    private Station destination;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="compartment_id")
    private Compartment compartment;

    public Person(String firstName, String lastName, Station destination) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.destination = destination;
    }

    public Person() {

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Station getDestination() {
        return destination;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    @Override
    public Long getId(){ return id;}
}
