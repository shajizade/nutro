import axios from "axios";

export default {
    login(user) {
        const formData = new FormData();
        formData.set("username", user.username);
        formData.set("password", user.password);
        return axios.post("/api/login", formData).then((res) => res.data);
    },
    logout() {
        return axios.get("/api/logout");
    },
    register(user) {
        return axios.post("/api/user/register", { user }).then((res) => res.data);
    },
    getCurrentUser() {
        return axios.get("/api/user/me").then((res) => res.data);
    },
};
