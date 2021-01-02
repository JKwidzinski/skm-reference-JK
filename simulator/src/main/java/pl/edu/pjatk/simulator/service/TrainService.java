package pl.edu.pjatk.simulator.service;

import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.simulator.exception.BadRequestException;
import pl.edu.pjatk.simulator.exception.NotFoundException;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.model.Train;
import pl.edu.pjatk.simulator.repository.CompartmentRepository;
import pl.edu.pjatk.simulator.repository.TrainRepository;




@Service
public class TrainService extends CrudService<Train> {
    private CompartmentService compartmentService;

    public TrainService(TrainRepository repository, CompartmentService compartmentService) {
        super(repository);
        this.compartmentService = compartmentService;
    }

    @Override
    public Train update(Train train) throws BadRequestException, NotFoundException{
        if (train.getId() == null) throw new BadRequestException();
        var entity = getUpdatedEntity(train);
        repository.save(entity);
        var compartments = entity.getCompartments();
        for(Compartment compartment : compartments){compartmentService.update(compartment);}
        return entity;
    }

    @Override
    public Train getUpdatedEntity(Train obj) throws NotFoundException {
        var currentTrain = repository.findById(obj.getId());
        if(currentTrain.isEmpty())throw new NotFoundException();
        return currentTrain.get();
    }
}
