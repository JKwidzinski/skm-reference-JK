package pl.edu.pjatk.simulator.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.service.TrainServiceConfiguration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Test
    public void basicTrainRepositoryTest(){
        Train train = new Train();
        TrainServiceConfiguration config = new TrainServiceConfiguration();
        List<Compartment> compartments = LongStream.rangeClosed(1, config.getNumberOfCompartments())
                .mapToObj(compartmentId -> new Compartment(config.getCompartmentCapacity()))
                .collect(Collectors.toList());
        train.setCompartments(compartments);
        train.setCurrentStation(Station.GDYNIA_GLOWNA);
        train.setCurrentPauseTime(0);
        train.setGoingToGdansk(true);
        trainRepository.save(train);

        Train fromDb = trainRepository.getOne(1L);

        assertEquals(train.getId(), fromDb.getId());
    }
}
