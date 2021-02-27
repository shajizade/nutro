package ir.haji.nutro.panel.research;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.dto.NutritionAmount;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.FoodNutrition;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.service.FoodService;
import ir.haji.nutro.panel.food.service.UnitService;
import ir.haji.nutro.panel.research.entity.*;
import ir.haji.nutro.panel.research.repo.CaseDetailRepo;
import ir.haji.nutro.panel.research.repo.CaseRepo;
import ir.haji.nutro.panel.research.repo.ResearchRepo;
import ir.haji.nutro.panel.research.repo.SimpleCaseDetailRepo;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.predefined.AdminRole;
import ir.haji.nutro.panel.um.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class ResearchService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FoodService foodService;
    @Autowired
    private CaseRepo caseRepo;
    @Autowired
    private CaseDetailRepo caseDetailRepo;
    @Autowired
    private ResearchRepo researchRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private SimpleCaseDetailRepo simpleCaseDetailRepo;

    public String output(Long id) {
        StringBuilder result = new StringBuilder();
        List<Case> cases = caseRepo.findByResearchId(id);
        for (Case aCase : cases) {
            NutritionFacts facts = new NutritionFacts();
            result.append(aCase.getName()).append(" | ").append(aCase.getCode()).append("\n");
            List<CaseDetail> details = getCaseDetails(aCase.getId());
            for (CaseDetail detail : details) {
                FullFood food = detail.getFood();
                if (food != null) {
                    result.append(food.getFood().getName()).append(" | ").append(detail.getAmount()).append("گرم \n");
                    for (FoodNutrition foodNutrition : food.getNutritions()) {
                        facts.add(foodNutrition.getNutrition(), detail.getAmount(), foodNutrition.getAmount().doubleValue());
                    }
                }
/*
                Recipe recipe = detail.getRecipe();
                if (recipe != null) {
                    result.append(recipe.getName()).append(" | ").append(detail.getAmount()).append("گرم \n");
                    NutritionFacts recipeFacts = foodService.getRecipeIngredients(recipe.getId());
                    facts.add(recipeFacts, detail.getAmount());
                }
*/
            }
            for (NutritionAmount fact : facts) {
                result.append("\t").append(fact.getNutrition().getName()).append(": ").append(fact.getAmount()).append(fact.getNutrition().getUnit().getName()).append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Page<Research> findAll(ResearchSpec spec) {
        if (!userService.getCurrentUser().hasAuthority(AdminRole.NAME))
            spec.setUserId(userService.getCurrentUser().getId());
        return researchRepo.findAll(spec, spec.getPageable());
    }

    public Research createResearch(Research research) {
        if (research.getName() == null || research.getName().isEmpty())
            throw new BadRequestException("نام مطالعه نمی‌تواند خالی باشد");
        research.setId(null);
        research.setUserId(userService.getCurrentUser().getId());
        research.setCreateDate(new Date());
        return researchRepo.save(research);
    }

    public void updateResearchName(Long id, Research research) {
        if (research == null || research.getName() == null || research.getName().isEmpty())
            throw new BadRequestException("نام جدید نمی‌تواند خالی باشد");
        Research researchDb = getResearch(id);
        researchDb.setName(research.getName());
        researchRepo.save(researchDb);
    }

    private Research getResearch(Long id) {
        Optional<Research> result = researchRepo.findById(id);
        if (!result.isPresent())
            throw new BadRequestException("خطا");
        return result.get();
    }

    public Page<Case> getCases(Long id, CaseSpec spec) {
        checkAuthority(id);
        spec.setResearchId(id);
        return caseRepo.findAll(spec, spec.getPageable());
    }

    private void checkAuthority(Long id) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.hasAuthority(AdminRole.CAPTION)) {
            Research research = getResearch(id);
            if (!research.getUserId().equals(currentUser.getId()))
                throw new BadRequestException("خطا");
        }
    }

    public Case addCaseToResearch(Long id, Case caseInstance) {
        if ((caseInstance.getName() == null || caseInstance.getName().isEmpty())
                && (caseInstance.getCode() == null || caseInstance.getCode().isEmpty()))
            throw new BadRequestException("نام و کد همزمان نمی‌تواند خالی باشد");
        checkAuthority(id);
        caseInstance.setId(null);
        caseInstance.setResearchId(id);
        caseInstance.setRegisterDate(new Date());
        return caseRepo.save(caseInstance);
    }

    public void removeCaseFromResearch(Long id, Long caseId) {
        checkAuthority(id);
        Case caseById = getCaseOfResearch(id, caseId);
        List<CaseDetail> details = getCaseDetails(id);
        if (details.size() > 0)
            throw new BadRequestException("ابتدا باید جزئيات کیس را پاک کنید");
        caseRepo.delete(caseById);
    }

    private List<CaseDetail> getCaseDetails(Long caseId) {
        return caseDetailRepo.findByCaseId(caseId);
    }

    private Case getCaseOfResearch(Long id, Long caseId) {
        Case caseById = getCaseById(caseId);
        if (!caseById.getResearchId().equals(id))
            throw new BadRequestException("خطا");
        return caseById;
    }

    private Case getCaseById(Long caseId) {
        Optional<Case> result = caseRepo.findById(caseId);
        if (!result.isPresent())
            throw new BadRequestException("خطا");
        return result.get();
    }

    public SimpleCaseDetail createOrUpdateCaseDetail(Long id, Long caseId, SimpleCaseDetail caseDetail) {
        if (caseDetail.getFoodId() == 0)
            throw new BadRequestException("باید غذا را مشخص کنید");
        if (caseDetail.getAmount() == null)
            throw new BadRequestException("مقدار نمی‌تواند خالی باشد");
        if (caseDetail.getDays() == null || caseDetail.getDays() < 1 || caseDetail.getDays() > 365)
            throw new BadRequestException("دوره‌ی زمانی نادرست است");
        if (caseDetail.getUnitId() == null)
            throw new BadRequestException("واحد نمی‌تواند خالی باشد");
        checkAuthority(id);
        getCaseOfResearch(id, caseId);
        unitService.getUnitUsage(caseDetail.getFoodId(), caseDetail.getUnitId());
        removeCaseDetailByCaseIdAndFoodId(caseId, caseDetail.getFoodId());
        caseDetail.setId(null);
        caseDetail.setCaseId(caseId);
        return simpleCaseDetailRepo.save(caseDetail);
    }

    private void removeCaseDetailByCaseIdAndFoodId(Long caseId, Long foodId) {
        caseDetailRepo.deleteByCaseIdAndFood_Id(caseId, foodId);
    }


    public void removeDetailFromCase(Long id, Long caseId, Long detailId) {
        checkAuthority(id);
        getCaseOfResearch(id, caseId);
        CaseDetail caseDetail = getCaseDetailById(detailId);
        if (!caseDetail.getCaseId().equals(caseId))
            throw new BadRequestException("خطا");
        caseDetailRepo.delete(caseDetail);
    }

    private CaseDetail getCaseDetailById(Long detailId) {
        Optional<CaseDetail> result = caseDetailRepo.findById(detailId);
        if (!result.isPresent()) {
            throw new BadRequestException("خطا");
        }
        return result.get();
    }

    public List<CaseDetail> getCaseDetails(Long id, Long caseId) {
        checkAuthority(id);
        getCaseOfResearch(id, caseId);
        List<CaseDetail> caseDetails = getCaseDetails(caseId);
        return caseDetails;
    }
}