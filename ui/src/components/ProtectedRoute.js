import React, { useContext } from "react";
import { Redirect, Route } from "react-router";
import { AuthContext } from "../context/authProvider";

const ProtectedRoute = (props) => {
    const { user } = useContext(AuthContext);
    console.log(user);
    if (user) {
        return <Route {...props} />;
    } else {
        return (
            <Redirect
                to={{ pathname: "/login", state: { from: props.location } }}
            />
        );
    }
};

export default ProtectedRoute;
