package ir.haji.nutro.panel.food.controller;

import ir.haji.nutro.panel.food.entity.Unit;
import ir.haji.nutro.panel.food.service.UnitService;
import ir.haji.nutro.panel.um.predefined.AdminRole;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


/**
 * Created by Saeedon 1/15/2018.
 */
@RestController
@RequestMapping("unit")
public class UnitController {
    @Autowired
    UnitService unitService;

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "", method = RequestMethod.GET)
    private Iterable<Unit> getAllUnits() {
        return unitService.getAll();
    }

    @RolesAllowed({AdminRole.NAME})
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Unit createUnit(@RequestBody Unit unit) {
        return unitService.createUnit(unit);
    }

    @RolesAllowed({AdminRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private Unit updateUnit(@PathVariable Long id, @RequestBody Unit unit) {
        return unitService.updateUnit(id, unit);
    }

}
