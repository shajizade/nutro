package ir.haji.nutro.panel.research;

import ir.haji.nutro.panel.food.dto.NutriotionAmount;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.FoodNutrition;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.entity.Recipe;
import ir.haji.nutro.panel.food.service.FoodService;
import ir.haji.nutro.panel.research.entity.Case;
import ir.haji.nutro.panel.research.entity.CaseDetail;
import ir.haji.nutro.panel.research.repo.CaseDetailRepo;
import ir.haji.nutro.panel.research.repo.CaseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String output(Long id) {
        StringBuilder result = new StringBuilder();
        List<Case> cases = caseRepo.findByResearchId(id);
        for (Case aCase : cases) {
            NutritionFacts facts = new NutritionFacts();
            result.append(aCase.getName()).append(" | ").append(aCase.getCode()).append("\n");
            List<CaseDetail> details = caseDetailRepo.findByCaseId(aCase.getId());
            for (CaseDetail detail : details) {
                FullFood food = detail.getFood();
                if (food != null) {
                    result.append(food.getFood().getName()).append(" | ").append(detail.getAmount()).append("گرم \n");
                    for (FoodNutrition foodNutrition : food.getNutritions()) {
                        facts.add(foodNutrition.getNutrition(), detail.getAmount(), foodNutrition.getAmount().doubleValue());
                    }
                }
                Recipe recipe = detail.getRecipe();
                if (recipe != null) {
                    result.append(recipe.getName()).append(" | ").append(detail.getAmount()).append("گرم \n");
                    NutritionFacts recipeFacts = foodService.getRecipe(recipe.getId());
                    facts.add(recipeFacts, detail.getAmount());
                }
            }
            for (NutriotionAmount fact : facts) {
                result.append("\t").append(fact.getNutrition().getName()).append(": ").append(fact.getAmount()).append(fact.getNutrition().getUnit().getName()).append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }
}