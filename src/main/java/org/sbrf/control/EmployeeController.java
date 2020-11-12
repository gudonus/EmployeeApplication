package org.sbrf.control;

import org.apache.log4j.Logger;
import org.sbrf.StoreTypes;
import org.sbrf.dao.DbObjectDao;
import org.sbrf.dao.MemoryObjectDao;
import org.sbrf.dao.ObjectDao;
import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.exception.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = Logger.getLogger(EmployeeController.class);

    public static StoreTypes baseStoreType;

    private static ObjectDao employeeDao;

    public static void createDao() {
        try {
            if (baseStoreType == StoreTypes.Database)
                employeeDao = new DbObjectDao();
            else
                employeeDao = new MemoryObjectDao();
        } catch (Exception exception) {
            logger.error("EmployeeController in create error: " + exception.toString());
            exception.printStackTrace();
        }
    }
    @GetMapping({"/", ""})
    public String index(Model model) {
        try {
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
        } catch(GetAllObjectException exception) {
            logger.error("EmployeeController in getAll error: " + exception.toString());
            exception.printStackTrace();
        }

        return "employee";
    }

    @GetMapping("/showall")
    public String showAll(Model model) {
        try {
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
        } catch(GetAllObjectException exception) {
            logger.error("EmployeeController cannot shawall: " + exception.toString());
            exception.printStackTrace();
        }

        return "showall";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());

        try {
            List<Function> functions = employeeDao.getAllFunctions();
            model.addAttribute("functions", functions);
        } catch (GetAllFunctionObjectException exception) {
            logger.error("EmployeeController cannot add: " + exception.toString());
            exception.printStackTrace();
        }
        return "add";
    }

    @GetMapping("/update/{employeeId}")
    public String update(@PathVariable("employeeId") String employeeId, Model model) {
        model.addAttribute("employee", employeeDao.get(Long.parseLong(employeeId)));

        try {
            List<Function> functions = employeeDao.getAllFunctions();
            model.addAttribute("functions", functions);
        } catch (GetAllFunctionObjectException exception) {
            logger.error("EmployeeController cannot update: " + exception.toString());
            exception.printStackTrace();
        }
        return "update";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee, Model model) {
        String wasAdded;
        try {
            employeeDao.add(employee);
            wasAdded = "Was Added Successfully";
        } catch (CannotAddObjectException exception) {
            wasAdded = "Error adding employee: " + exception.toString();
            exception.printStackTrace();
        }

        model.addAttribute("wasAddedResult", wasAdded);
        logger.info("\t addEmployee: " + wasAdded);

        return "add_employee";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, Model model) {
        String wasUpdated;
        try {
            employeeDao.update(employee);
            wasUpdated = "Was Added Successfully";
        } catch (CannotUpdateObjectException exception) {
            wasUpdated = "Error adding employee: " + exception.toString();
            exception.printStackTrace();
        }

        model.addAttribute("wasUpdatedResult", wasUpdated);
        logger.info("\t updateEmployee: " + wasUpdated);

        return "update_employee";
    }

    @GetMapping("/show")
    public String show() {
        return "show";
    }

    @GetMapping("/show/{employeeId}")
    public String show(@PathVariable("employeeId") String employeeId, Model model) {
        Employee employee = employeeDao.get(Integer.parseInt(employeeId));

        model.addAttribute("id", employee.getId());
        model.addAttribute("firstName", employee.getFirstName());
        model.addAttribute("surName", employee.getSurName());
        model.addAttribute("address", employee.getAddress());
        model.addAttribute("phone", employee.getPhone());

        return "show_employee";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        try {
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("EmployeeController cannot delete: " + exception.toString());
        }
        return "delete";
    }

    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId, Model model) {
        try {
            employeeDao.delete(Integer.parseInt(employeeId));
        } catch (CannotDeleteObjectException exception) {
            exception.printStackTrace();
            logger.error("EmployeeController cannot delete: " + exception.toString());
        }
        logger.info("\t Employee was deleted!");

        try {
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
        } catch(GetAllObjectException exception) {
            exception.printStackTrace();
            logger.error("EmployeeController in delete getAll problem: " + exception.toString());
        }
        return "showall";
    }
}
