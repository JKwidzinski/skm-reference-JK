package pl.edu.pjatk.simulator.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.model.Station;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void basicPersonRepositoryTest(){
        Person person = new Person();
        person.setFirstName("Jakub");
        person.setLastName("Kwidzinski");
        person.setDestination(Station.GDYNIA_GLOWNA);
        personRepository.save(person);

        Person fromDb = personRepository.getOne(1L);

        assertEquals(person.getId(), fromDb.getId());
    }
}
