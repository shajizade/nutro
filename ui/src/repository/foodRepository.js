import axios from "axios";

export default {
    getFoods(search) {
        return axios.get("/api/food/", { params: { ...search } }).then((res) => res.data);
    },
    calculate(rows, portionGrams) {
        return axios.put("/api/food/calculate", [...rows], { params: { portionGrams } }).then((res) => res.data);
    },
};
