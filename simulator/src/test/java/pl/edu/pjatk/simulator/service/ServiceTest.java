package pl.edu.pjatk.simulator.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.PersonRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ServiceTest {

    @Test
    public void serviceTest() {
        TrainRepository trainRepository = Mockito.mock(TrainRepository.class);
        CompartmentRepository compartmentRepository = Mockito.mock(CompartmentRepository.class);
        PersonRepository personRepository = Mockito.mock(PersonRepository.class);

        CrudService<Person> personService = new PersonService(personRepository);
        CrudService<Compartment> compartmentService = new CompartmentService(compartmentRepository, (PersonService) personService);
        CrudService<Train> trainService = new TrainService(trainRepository,(CompartmentService) compartmentService);

        Random random = new Random();
        TrainServiceConfiguration config = new TrainServiceConfiguration();
        List<Train> trains = IntStream.rangeClosed(1, config.getNumberOfTrains())
                .mapToObj(trainId -> {
                    List<Compartment> compartments = LongStream.rangeClosed(1, config.getNumberOfCompartments())
                            .mapToObj(compartmentId -> new Compartment(config.getCompartmentCapacity()))
                            .collect(Collectors.toList());
                    Station[] stations = Station.values();
                    int lastStationOrdinalBound = stations[stations.length - 1].ordinal();

                    Train train = new Train(compartments, stations[random.nextInt(lastStationOrdinalBound)], random.nextBoolean());
                    compartments.forEach(c -> {
                        List<Person> people = PersonGenerator.generatePeople(train.getCurrentStation());
                        people.forEach(c::embark);
                        personService.addAll(people);
                    });
                    train.setCompartments(compartments);
                    compartmentService.addAll(compartments);
                    return train;
                }).collect(Collectors.toList());
        trainService.addAll(trains);
        assertTrue(trainRepository != null);
        assertTrue(compartmentRepository != null);
        assertTrue(personRepository != null);
    }
}
