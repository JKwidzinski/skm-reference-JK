package pl.edu.pjatk.simulator.service;

import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.exception.BadRequestException;
import pl.edu.pjatk.simulator.exception.NotFoundException;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Person;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;



@Service
public class CompartmentService extends CrudService<Compartment>{
    private final PersonService personService;

    public CompartmentService(CompartmentRepository repository, PersonService personService) {
        super(repository);
        this.personService = personService;
    }

    @Override
    public Compartment update(Compartment compartment) throws BadRequestException, NotFoundException {
        if (compartment.getId() == null) throw new BadRequestException();
        var entity = getUpdatedEntity(compartment);
        repository.save(entity);
        var people = entity.getOccupants();
        for(Person person : people){personService.update(person);}
        return entity;
    }

    @Override
    public Compartment getUpdatedEntity(Compartment obj) throws NotFoundException {
        var currentCompartment = repository.findById(obj.getId());
        if(currentCompartment.isEmpty())throw new NotFoundException();
        return currentCompartment.get();
    }
}