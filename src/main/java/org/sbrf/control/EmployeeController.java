package org.sbrf.control;

import org.sbrf.employee.Employee;
import org.sbrf.employee.EmployeeDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping({"/", ""})
    public String index() {
        return "employee";
    }

    @GetMapping("/showall")
    public String showAll(Model model) {
        EmployeeDao employeeDao = new EmployeeDao();
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
//        model.addAttribute("employee", employee);
        String wasAdded = "Error! Was not added!";

        employee.setFirstName(employee.getFirstName());
        employee.setSurName(employee.getSurName());

        try {
            EmployeeDao employeeDao = new EmployeeDao();
            employeeDao.add(employee);

            wasAdded = "Was Added Successfully";
        } catch (Exception exception) {
            wasAdded = "There where an exception!";
        }
        model.addAttribute("wasAddedResult", wasAdded);

        return "add_employee";
    }

    @GetMapping("/show")
    public String show() {
        return "show";
    }

    @GetMapping("/show/{employeeId}")
    public String show(@PathVariable("employeeId") String employeeId, Model model) {

        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.getById(Integer.parseInt(employeeId));

        model.addAttribute("id", employee.getId());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("surName", employee.getSurName());

        return "show_employee";
    }

    @GetMapping("/delete")
    public String delete() {
        return "delete";
    }

    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.delete(Integer.parseInt(employeeId));

        return "showall";
    }
}
