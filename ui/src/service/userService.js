import useRepository from "../repository/useRepository";
export default {
    login(user) {
        return useRepository.login(user);
    },
    logout() {
        return useRepository.logout();
    },
    register(user) {
        return useRepository.register(user);
    },
    getCurrentUser() {
        return useRepository.getCurrentUser();
    },
};
