package pl.edu.pjatk.simulator.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.exception.BadRequestException;
import pl.edu.pjatk.simulator.exception.NotFoundException;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Train;


import java.util.Collection;
import java.util.List;

@Service
@ConfigurationProperties(prefix = "skm")
public class SkmService {
    private final TrainService trainService;
    private final CompartmentService compartmentService;
    private final PersonService personService;

    public SkmService(TrainService trainService, CompartmentService compartmentService, PersonService personService) {
        this.trainService = trainService;
        this.compartmentService = compartmentService;
        this.personService = personService;
    }

    public void moveTimeForward() throws BadRequestException, NotFoundException {
        List<Train> trains = trainService.getAll();
        for(Train train : trains) {
            train.move();
            Collection<Compartment> compartments = train.getCompartments();
            try {
                trainService.update(train);
                for(Compartment compartment : compartments){
                    compartmentService.update(compartment);
                    Collection<Person> people = compartment.getOccupants();
                    for(Person person : people){
                        personService.update(person);
                    }
                }
            } catch (BadRequestException e) {
                throw new BadRequestException();
            } catch (NotFoundException e) {
                throw new NotFoundException();
            }
        }

    }

    public TrainService getTrainService() {
        return trainService;
    }

    public CompartmentService getCompartmentService() {
        return compartmentService;
    }

    public PersonService getPersonService() {
        return personService;
    }
}
