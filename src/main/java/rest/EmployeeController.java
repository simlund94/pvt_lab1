package rest;

import domain.Employee;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import service.ServiceRunner;
import service.employee.GetAllEmployeesService;
import service.employee.GetEmployeeByIdService;

import java.util.List;

/**
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-03-17
 */
@Path("/employees")
public class EmployeeController {

    private ServiceRunner runner;

    @Inject
    public EmployeeController(ServiceRunner runner) {
        this.runner = runner;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Employee get(@PathParam("id") int id) {
        return runner.execute(new GetEmployeeByIdService(id));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public List<Employee> getAll() {
        return runner.execute(new GetAllEmployeesService());
    }
}
