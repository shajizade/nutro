package ir.haji.nutro.panel.research;

import ir.haji.nutro.panel.food.entity.Food;
import ir.haji.nutro.panel.research.entity.*;
import ir.haji.nutro.panel.research.repo.CaseDetailRepo;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


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
    @RequestMapping(value = "", method = RequestMethod.GET)
    private Page<Research> getMyResearchs(ResearchSpec spec) {
        return researchService.findAll(spec);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/type", method = RequestMethod.GET)
    private List<ResearchType> getResearchTypes() {
        return researchService.getAllResearchTypes();
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Research createResearch(@RequestBody Research research) {
        return researchService.createResearch(research);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private void updateResearchName(@PathVariable Long id, @RequestParam Research research) {
        researchService.updateResearchName(id, research);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case", method = RequestMethod.GET)
    private Page<Case> getCases(@PathVariable Long id, CaseSpec spec) {
        return researchService.getCases(id, spec);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case", method = RequestMethod.POST)
    private Case addCase(@PathVariable Long id, @RequestBody Case caseInstance) {
        return researchService.addCaseToResearch(id, caseInstance);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case/{caseId}", method = RequestMethod.DELETE)
    private void removeCase(@PathVariable Long id, @PathVariable Long caseId) {
        researchService.removeCaseFromResearch(id, caseId);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case/{caseId}/toggle", method = RequestMethod.PUT)
    private void toggleCase(@PathVariable Long id, @PathVariable Long caseId) {
        researchService.toggleCase(id, caseId);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case/{caseId}/detail", method = RequestMethod.GET)
    private List<CaseDetail> getCaseDetail(@PathVariable Long id, @PathVariable Long caseId) {
        return researchService.getCaseDetails(id, caseId);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case/{caseId}/detail", method = RequestMethod.POST)
    private SimpleCaseDetail createOrUpdateCaseDetail(@PathVariable Long id, @PathVariable Long caseId, @RequestBody SimpleCaseDetail caseInstance) {
        return researchService.createOrUpdateCaseDetail(id, caseId, caseInstance);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/case/{caseId}/detail/{detailId}", method = RequestMethod.DELETE)
    private void removeCase(@PathVariable Long id, @PathVariable Long caseId, @PathVariable Long detailId) {
        researchService.removeDetailFromCase(id, caseId, detailId);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private String getResearchOutput(@PathVariable Long id) {
        return researchService.output(id);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/foods", method = RequestMethod.GET)
    private List<Food> getResearchFoods(@PathVariable Long id) {
        return researchService.getResearchFoods(id);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/excel", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public
    @ResponseBody
    byte[] getResearchExcel(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");
        return researchService.getResearchExcel(id);
    }

}
