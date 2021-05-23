package ir.haji.nutro.panel.research;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.dto.NutritionAmount;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.Food;
import ir.haji.nutro.panel.food.entity.FoodNutrition;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.entity.Nutrition;
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
import ir.haji.nutro.util.Doubler;
import ir.haji.nutro.util.ExcelWorker;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (research.getResearchType() == null || research.getResearchType().getId() == null)
            throw new BadRequestException("نوع مطالعه نمی‌تواند خالی باشد");
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

    public List<Case> getTotalAcceptedCases(Long id) {
        checkAuthority(id);
        return caseRepo.findAllByResearchIdAndStatus(id, Case.STATUS_ACCEPTED);
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
        caseInstance.setStatus(Case.STATUS_CREATED);
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

    public List<Food> getResearchFoods(Long id) {
        Research research = getResearch(id);
        return foodService.getFoodsByResearchTypeId(research.getResearchType().getId());
    }


    public byte[] getResearchExcel(Long id) throws IOException {
        List<Case> cases = getTotalAcceptedCases(id);
        if (cases == null || cases.size() == 0)
            throw new BadRequestException("تحقیق شما هیچ موردی برای خروجی گرفتن ندارد");
        List<Nutrition> nutriotions = foodService.getAllNutriotions();
        ExcelWorker excel = new ExcelWorker();
        fillExcelCasesSheet(excel, cases);
        for (Case aCase : cases) {
            fillExcelCaseSheet(nutriotions, excel, aCase);
        }

        return excel.toByteArray();
    }

    private void fillExcelCaseSheet(List<Nutrition> nutriotions, ExcelWorker excel, Case aCase) {
        excel.addSheet(aCase.getName());
        XSSFCellStyle style = excel.getStyleMaker().bold().center().fontSize(10).getStyle();
        XSSFCellStyle sumStyle = excel.getStyleMaker().bold().center().fontSize(10).background(252, 251, 174).getStyle();

        excel.addRow()
                .setCellValue(aCase.getName(), style)
                .merge(0, 0, 0, 5);

        excel.addRow()
                .setCellValue("شناسه")
                .setCellValue("شماره آیتم")
                .setCellValue("منبع")
                .setCellValue("ماده‌ی غذایی")
                .setCellValue("مقدار مصرف سالانه")
                .setCellValue("واحد")
                .setCellValue("وزن مصرف سالانه(گرم)");

        nutriotions.forEach(nut -> excel.setCellValue(nut.getName()));

        List<CaseDetail> caseDetails = getCaseDetails(aCase.getId());
        caseDetails.forEach(detail -> {
            excel.addRow()
                    .setCellValue(detail.getFood().getFood().getId())
                    .setCellValue(detail.getFood().getFood().getItemNumber())
                    .setCellValue(detail.getFood().getFood().getSource())
                    .setCellValue(detail.getFood().getFood().getName())
                    .setCellValue(detail.getPerYearAmount())
                    .setCellValue(detail.getUnit().getName())
                    .setCellValue(new Doubler(detail.getPerYearAmount()).multiply(detail.getScale()).toDouble());

            nutriotions.forEach(nut -> excel.setCellValue(findNutAmount(detail, nut)));
        });

        excel.addRow().setCellValue("مجموع", sumStyle)
                .setCellValue("", sumStyle)
                .setCellValue("", sumStyle)
                .setCellValue("", sumStyle)
                .setCellValue("", sumStyle)
                .setCellValue("", sumStyle);
        for (int col = 0; col < nutriotions.size() + 1; col++) {
            String columnAddress = ExcelWorker.getColumnAddress(col + 7);
            excel.setCellFormula("sum(" + columnAddress + "3:" + columnAddress + (caseDetails.size() + 2) + ")", sumStyle);
        }
    }

    private void fillExcelCasesSheet(ExcelWorker excel, List<Case> cases) {
        excel.addSheet("لیست موارد");
        excel.addRow()
                .setCellValue("کد")
                .setCellValue("نام و نام خانوادگی")
                .setCellValue("جنسیت")
                .setCellValue("سن")
                .setCellValue("قد")
                .setCellValue("وزن")
                .setCellValue("BMI")
                .setCellValue("دور کمر")
                .setCellValue("دور باسن")
                .setCellValue("دور باسن به دور کمر")
                .setCellValue("میزان فعالیت")
                .setCellValue("بیماری");
        cases.forEach(aCase -> {
            excel.addRow()
                    .setCellValue(aCase.getCode())
                    .setCellValue(aCase.getName())
                    .setCellValue(aCase.getGender())
                    .setCellValue(aCase.getAge())
                    .setCellValue(aCase.getHeight())
                    .setCellValue(aCase.getWeight())
                    .setCellValue(aCase.getBmi())
                    .setCellValue(aCase.getWaist())
                    .setCellValue(aCase.getHip())
                    .setCellValue(aCase.getWaistToHip())
                    .setCellValue(aCase.getActivity())
                    .setCellValue(aCase.getSickness());
        });
    }

    private BigDecimal findNutAmount(CaseDetail detail, Nutrition nut) {
        List<FoodNutrition> collect = detail.getFood().getNutritions().stream()
                .filter(fn -> nut.getId().equals(fn.getNutrition().getId()))
                .collect(Collectors.toList());
        return collect.isEmpty() ? null :
                new Doubler(detail.getAmount())
                        .multiply(detail.getScale())
                        .multiply(collect.get(0).getAmount())
                        .toBigDecimal();
    }

    public void toggleCase(Long id, Long caseId) {
        checkAuthority(id);
        Case caseById = getCaseById(caseId);
        if (caseById.getResearchId().equals(id)) {
            if (Case.STATUS_CREATED.equals(caseById.getStatus())) {
                List<CaseDetail> caseDetails = getCaseDetails(caseId);
                if (caseDetails == null || caseDetails.size() == 0)
                    throw new BadRequestException("نمی‌توانید یک کیس بدون مورد را تایید کنید");
            }
            caseById.setStatus(Case.STATUS_CREATED.equals(caseById.getStatus()) ? Case.STATUS_ACCEPTED : Case.STATUS_CREATED);
            caseRepo.save(caseById);
        }
    }

    public List<ResearchType> getAllResearchTypes() {
        return researchRepo.getAllResearchTypes();
    }
}