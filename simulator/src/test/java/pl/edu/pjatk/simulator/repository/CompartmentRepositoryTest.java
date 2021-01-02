package pl.edu.pjatk.simulator.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.service.TrainServiceConfiguration;
import pl.edu.pjatk.simulator.util.PersonGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CompartmentRepositoryTest {

    @Autowired
    private CompartmentRepository compartmentRepository;

    @Test
    public void basicCompartmentRepositoryTest(){
        TrainServiceConfiguration config = new TrainServiceConfiguration();
        Compartment compartment = new Compartment();
        List<Person> people = PersonGenerator.generatePeople(Station.GDYNIA_GLOWNA);
        compartment.setOccupants(people);
        compartment.setCapacity(config.getCompartmentCapacity());
        compartmentRepository.save(compartment);

        Compartment fromDb = compartmentRepository.getOne(1L);

        assertEquals(compartment.getId(), fromDb.getId());
    }
}
