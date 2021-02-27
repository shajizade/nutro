package ir.haji.nutro.panel.food.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.entity.Unit;
import ir.haji.nutro.panel.food.entity.UnitUsage;
import ir.haji.nutro.panel.food.repo.UnitRepo;
import ir.haji.nutro.panel.food.repo.UnitUsageRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class UnitService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UnitUsageRepo unitUsageRepo;
    private UnitRepo unitRepo;

    public UnitUsage getUnitUsage(Long foodId, Long unitId) {
        foodId = foodId == null ? 0 : foodId;
        List<UnitUsage> result = unitUsageRepo.findByFoodIdAndUnitId(foodId, unitId);
        if (result == null || result.size() == 0)
            throw new BadRequestException("خطا در واحد اندازه‌گیری");
        return result.get(0);
    }

    public Unit getUnitById(Long unitId) {
        Optional<Unit> result = unitRepo.findById(unitId);
        if (!result.isPresent())
            throw new BadRequestException("واحد اندازه گیری غلط");
        return result.get();
    }

    public void save(List<UnitUsage> usages) {
        unitUsageRepo.saveAll(usages);
    }

    public Iterable<Unit> getAll() {
        return unitRepo.findAll();

    }

    public Unit createUnit(Unit unit) {
        unit.setId(null);
        if (unit.getName() == null || unit.getName().isEmpty())
            throw new BadRequestException("نام واحد نباید خالی باشد");
        if (unit.getCode() == null || unit.getCode().isEmpty())
            throw new BadRequestException("کد واحد نباید خالی باشد");
        return unitRepo.save(unit);
    }

    public Unit updateUnit(Long id, Unit unit) {
        Unit unitDb = getUnitById(id);
        if (unit.getName() != null && !unit.getName().isEmpty())
            unitDb.setName(unit.getName());
        if (unit.getCode() != null && !unit.getCode().isEmpty())
            unitDb.setCode(unit.getCode());
        return unitRepo.save(unitDb);
    }
}