package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.exception.NotFoundException;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.PersonRepository;

@Service
public class PersonService extends CrudService<Person> {

    public PersonService(PersonRepository repository){
        super(repository);
    }


    @Override
    public Person getUpdatedEntity(Person obj) throws NotFoundException {
        var currentPerson = repository.findById(obj.getId());
        if(currentPerson.isEmpty())throw new NotFoundException();
        return currentPerson.get();
    }
}
