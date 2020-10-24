package ir.haji.nutro.panel.food.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.entity.UnitUsage;
import ir.haji.nutro.panel.food.repo.UnitUsageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class UnitService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UnitUsageRepo unitUsageRepo;

    public UnitUsage getUnitUsage(Long foodId, Long recipeId, Long unitId) {
        recipeId = recipeId == null ? 0 : recipeId;
        foodId = foodId == null ? 0 : foodId;

        List<UnitUsage> result = unitUsageRepo.findByFoodIdAndRecipeIdAndUnitId(foodId, recipeId, unitId);
        if (result == null || result.size() == 0)
            throw new BadRequestException("خطا در واحد اندازه‌گیری");
        return result.get(0);
    }
}