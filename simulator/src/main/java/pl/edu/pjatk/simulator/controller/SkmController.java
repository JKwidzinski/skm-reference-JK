package pl.edu.pjatk.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Station;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.service.CompartmentService;
import pl.edu.pjatk.simulator.service.SkmService;
import pl.edu.pjatk.simulator.service.TrainService;
import pl.edu.pjatk.simulator.service.TrainServiceConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/skm")
public class SkmController {

    @Autowired
    SkmService skmService;

    @GetMapping()
    @RequestMapping("/go")
    public ResponseEntity<List<Map<String, Object>>> moveTime() {
        try {
            skmService.moveTimeForward();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    @RequestMapping("/start")
    public ResponseEntity initializeDb() {
        try {
            Random random = new Random();
            TrainServiceConfiguration config = new TrainServiceConfiguration();
            TrainService trainService = skmService.getTrainService();
            CompartmentService compartmentService = skmService.getCompartmentService();

            List<Train> trains = IntStream.rangeClosed(1, config.getNumberOfTrains())
                    .mapToObj(trainId -> {
                        List<Compartment> compartments = LongStream.rangeClosed(1, config.getNumberOfCompartments())
                                .mapToObj(compartmentId -> new Compartment(config.getCompartmentCapacity()))
                                .collect(Collectors.toList());

                        compartmentService.addAll(compartments);

                        Station[] stations = Station.values();
                        int lastStationOrdinalBound = stations[stations.length - 1].ordinal();


                        return new Train(compartments, stations[random.nextInt(lastStationOrdinalBound)], random.nextBoolean());
                    }).collect(Collectors.toList());
            trainService.addAll(trains);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
