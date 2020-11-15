package org.sbrf.control;

import org.apache.log4j.Logger;
import org.sbrf.dao.*;
import org.sbrf.dto.Employee;
import org.sbrf.dto.Function;
import org.sbrf.enums.StoreTypes;
import org.sbrf.exception.AddObjectException;
import org.sbrf.exception.DeleteObjectException;
import org.sbrf.exception.GetObjectException;
import org.sbrf.exception.UpdateObjectException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeesController {

    private static final Logger logger = Logger.getLogger(EmployeesController.class);

    public static DaoTypeController datastore;

    private static ObjectDao employeeDao;

    public static void createDao(StoreTypes dataStoreType) {
        if (employeeDao != null) {
            logger.error("EmployeeController: попытка поменять тип хранилища.");
            return;
        }

        datastore = DaoTypeController.applyType(dataStoreType);
        try {
            if (datastore.getType() == StoreTypes.Database)
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
        } catch (GetObjectException exception) {
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
        } catch (GetObjectException exception) {
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
        } catch (GetObjectException exception) {
            logger.error("EmployeeController cannot add: " + exception.toString());
            exception.printStackTrace();
        }
        return "add";
    }

    @GetMapping("/update/{employeeId}")
    public String update(@PathVariable("employeeId") String employeeId, Model model) {
        FilterDaoEmployee filter = new FilterDaoEmployee("employeeId", employeeId);
        try {
            model.addAttribute("employee", employeeDao.get(filter));
        } catch (Exception exception) {
            logger.error("EmployeeController cannot update: " + exception.toString());
            exception.printStackTrace();
        }

        try {
            List<Function> functions = employeeDao.getAllFunctions();
            model.addAttribute("functions", functions);
        } catch (GetObjectException exception) {
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
        } catch (AddObjectException exception) {
            wasAdded = "Error adding employee: " + exception.toString();
            exception.printStackTrace();
        }

        model.addAttribute("wasAddedResult", wasAdded);
        logger.info("\t addEmployee: " + wasAdded);

        return "result";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee, Model model) {
        String wasUpdated;
        try {
            employeeDao.update(employee);
            wasUpdated = "Was Updated Successfully";
        } catch (UpdateObjectException exception) {
            wasUpdated = "Error updating employee: " + exception.toString();
            exception.printStackTrace();
        }

        model.addAttribute("wasUpdatedResult", wasUpdated);
        logger.info("\t updateEmployee: " + wasUpdated);

        return "update_employee";
    }

/*    @GetMapping("/show")
    public String show() {
        return "show";
    }*/

    @GetMapping("/show")
    public String show(@RequestParam(name = "surname", required = false) String surname,
                       @RequestParam(name = "id", required = false) String id,
                       @RequestParam(name = "phone", required = false) String phone,
                       Model model) {

        if ((surname == null) && (id == null) && (phone == null))
            return "show";

        FilterDaoEmployee filter = new FilterDaoEmployee("surname", surname);
        filter.add("employeeId", id);
        filter.add("phone", phone);
        try {
            Employee employee = (Employee) employeeDao.get(filter);
            model.addAttribute("id", employee.getId());
            model.addAttribute("firstName", employee.getFirstName());
            model.addAttribute("surName", employee.getSurName());
            model.addAttribute("address", employee.getAddress());
            model.addAttribute("phone", employee.getPhone());

            if (employee.isValid())
                return "show_employee";

        } catch (Exception exception) {
            logger.error("EmployeeController: show employee: " + exception.getMessage());
            exception.printStackTrace();

            model.addAttribute("wasAddedResult", "Employee not found!");
            return "result";
        }

        return "showall";
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
            FilterDaoEmployee filter = new FilterDaoEmployee("employeeId", employeeId);
            employeeDao.delete(filter);
        } catch (DeleteObjectException exception) {
            exception.printStackTrace();
            logger.error("EmployeeController cannot delete: " + exception.toString());
        }
        logger.info("\t Employee was deleted!");

        try {
            List<Employee> employees = employeeDao.getAll();
            model.addAttribute("employees", employees);
        } catch (GetObjectException exception) {
            exception.printStackTrace();
            logger.error("EmployeeController in delete getAll problem: " + exception.toString());
        }
        return "showall";
    }
}
