package ir.haji.nutro.panel.research;

import ir.haji.nutro.panel.research.entity.Case;
import ir.haji.nutro.panel.research.entity.CaseDetail;
import ir.haji.nutro.panel.research.repo.CaseDetailRepo;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


/**
 * Created by Saeedon 1/15/2018.
 */
@RestController
@RequestMapping("research")
public class ResearchController {

    @Autowired
    CaseDetailRepo repo;
    @Autowired
    private ResearchService researchService;

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/", method = RequestMethod.POST)
    private Iterable<CaseDetail> getRecipe(@RequestBody Case caseVal) {
        return repo.findAll();
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private String getResearchOutput(@PathVariable Long id) {
        return researchService.output(id);
    }

}
