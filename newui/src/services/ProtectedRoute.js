import React, {useContext} from "react";
import {Redirect, Route} from "react-router-dom";
import {AuthContext} from "../context/Auth/AuthProvider";

const ProtectedRoute = ({component: Component, ...rest}) => {
  const {currentUser} = useContext(AuthContext);
  return (
    <Route {...rest} render={(props) => {
      if (!currentUser) {
        return <Redirect to={{pathname: "/login", state: {from: props.location}}}/>;
      } else {
        return <Component {...props}/>;
      }
    }}/>
  );
};

export default ProtectedRoute;
