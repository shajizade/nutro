import React from "react";
import { useMutation, useQuery } from "react-query";
import userService from "../service/userService";
import { useHistory } from "react-router-dom";

export const AuthContext = React.createContext({});
const AuthProvider = (props) => {
    const history = useHistory();
    const {
        data: user,
        isLoading: userLoading,
        refetch: refetchUser,
    } = useQuery("currentUser", userService.getCurrentUser, {
        onSuccess: () => history.push("/"),
    });
    const [login, { isLoading: loginLoading }] = useMutation(
        userService.login,
        {
            onSuccess: refetchUser,
        }
    );
    const [logout] = useMutation(userService.logout, {
        onSuccess: () => history.push("/login"),
    });
    return (
        <AuthContext.Provider
            value={{
                login,
                logout,
                user,
                loading: userLoading || loginLoading,
            }}
        >
            {props.children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;
