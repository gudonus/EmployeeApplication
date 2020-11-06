package org.sbrf.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping({"/", ""})
    public String index() {
        return "employee";
    }

    @GetMapping("/showall")
    public String showAll() {
        return "showall";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/show")
    public String show() {
        return "show";
    }

    @GetMapping("/show/{employeeId}")
    public String show(@PathVariable("employeeId") String employeeId, Model model) {
        model.addAttribute(employeeId);
        return "show";
    }

    @GetMapping("/delete/{employeeId}")
    public String delete(@PathVariable("employeeId") String employeeId, Model model) {
        model.addAttribute(employeeId);
        return "delete";
    }

    @GetMapping("/delete")
    public String delete() {
        return "delete";
    }
}
