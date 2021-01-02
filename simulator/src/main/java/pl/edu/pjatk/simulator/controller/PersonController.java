package pl.edu.pjatk.simulator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController extends CrudController<Person>{
    public PersonController(PersonService service){
        super(service);
    }
}
