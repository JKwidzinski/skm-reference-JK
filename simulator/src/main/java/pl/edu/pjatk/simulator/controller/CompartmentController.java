package pl.edu.pjatk.simulator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.simulator.model.Compartment;
import pl.edu.pjatk.simulator.service.CompartmentService;

@RestController
@RequestMapping("/compartments")
public class CompartmentController extends CrudController<Compartment> {
    public CompartmentController(CompartmentService service){
        super(service);
    }
}
