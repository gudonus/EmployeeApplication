package org.sbrf.control;

import org.sbrf.enums.StoreTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{storeTypeId}")
    public String indexStoreType(@PathVariable("storeTypeId") String storeTypeId) {
        if (storeTypeId.equals("1"))
            EmployeesController.createDao(StoreTypes.Database);
        else
            EmployeesController.createDao(StoreTypes.MemoryStore);

        return "employee";
    }
}
