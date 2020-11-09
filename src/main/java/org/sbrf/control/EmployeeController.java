package org.sbrf.control;

import org.apache.log4j.Logger;
import org.sbrf.employee.Employee;
import org.sbrf.employee.EmployeeDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeDao employeeDao;

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    public EmployeeController() {
        try {
            this.employeeDao = new EmployeeDao();
        } catch (Exception exception) {
            logger.error("EmployeeDao creat error: " + exception.toString());
        }
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        List<Employee> employees = employeeDao.getAll();
        model.addAttribute("employees", employees);

        return "employee";
    }

    @GetMapping("/showall")
    public String showAll(Model model) {
        List<Employee> employees = employeeDao.getAll();
        model.addAttribute("employees", employees);

        return "showall";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, Model model) {
        employee.setFirstName(employee.getFirstName());
        employee.setSurName(employee.getSurName());

        String wasAdded;
        if (employeeDao.add(employee))
            wasAdded = "Was Added Successfully";
        else
            wasAdded = "There where an exception!";

        logger.info("\t addEmployee: " + wasAdded);
        model.addAttribute("wasAddedResult", wasAdded);

        return "add_employee";
    }

    @GetMapping("/show")
    public String show() {
        return "show";
    }

    @GetMapping("/show/{employeeId}")
    public String show(@PathVariable("employeeId") String employeeId, Model model) {
        Employee employee = employeeDao.getById(Integer.parseInt(employeeId));

        model.addAttribute("id", employee.getId());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("surName", employee.getSurName());

        return "show_employee";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        List<Employee> employees = employeeDao.getAll();
        model.addAttribute("employees", employees);

        return "delete";
    }

    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId, Model model) {
        employeeDao.delete(Integer.parseInt(employeeId));
        logger.info("\t addEmployee: ");

        List<Employee> employees = employeeDao.getAll();
        model.addAttribute("employees", employees);

        return "showall";
    }
}
