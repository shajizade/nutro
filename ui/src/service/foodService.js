import foodRepository from "../repository/foodRepository";

export default {
    getFoods(key, search) {
        return foodRepository.getFoods(search);
    },
    calculate(rows, portionGrams) {
        return foodRepository.calculate(
            rows.map((row) => ({ number: row.food.itemNumber, gram: row.gram })),
            portionGrams
        );
    },
};
