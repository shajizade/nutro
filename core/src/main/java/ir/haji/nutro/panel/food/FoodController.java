package ir.haji.nutro.panel.food;

import ir.haji.nutro.panel.food.entity.Food;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.service.FoodService;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


/**
 * Created by Saeedon 1/15/2018.
 */
@RestController
@RequestMapping("food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Food getById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/full", method = RequestMethod.GET)
    private FullFood getFullById(@PathVariable Long id) {
        return foodService.getFullFoodById(id);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    private String getFullById(@RequestParam List<Long> ids) {
        return foodService.calculate(ids);
    }

}
