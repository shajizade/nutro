import React, {useEffect, useContext} from "react";
import authApis from "../../api/authApis";
import useStorage from "../../services/UseStorage";
import {AlertContext} from "../../context/AlertProvider";
export const AuthContext = React.createContext();

const AuthProvider = (props) => {

  const alert = useContext(AlertContext);
  const loginApi = authApis.useLoginApi({showSuccessMessage: false, shouldLogout: false, showError: false});
  const userApi = authApis.useUserApi({showError: false, initialLoading: true});
  const logoutApi = authApis.useLogoutApi({successMessage: null});
  const [currentUser,setCurrentUser]= useStorage('currentUser', null);
  useEffect(() => {
    getUser();
    // eslint-disable-next-line
  }, []);
  const getUser = () => {
    userApi.call()
      .then((resp)=> {
        setCurrentUser(resp);
      })
      .catch(()=> {
        setCurrentUser(null);
      });
  };

  const login = (user) => {
    return loginApi.call({body: user, forceJson: true})
      .then((resp)=> {
        setCurrentUser(resp);
      }).catch(ex=> {
        alert.addToast('گذرنامه یا نام کاربری به درستی وارد نشده است', 'خطا', 'warning');
      });
  };


  const logout = () => {
    return logoutApi.call()
      .then(() => {
        setCurrentUser(null);
      });
  };

  const getAllRoles = () => {
    let roles = [];
    if (userApi.response && userApi.response.roles) {
      userApi.response.roles.forEach(role => {
        roles.push(role.role);
        if (role.parent) {
          roles.push(role.parent.role)
        }
      })
    }
    return [...new Set(roles)];
  };
  const isAdmin = ()=> {
    return getAllRoles().includes("ADMIN")
  };

  const hasRole = (role) => {
    return getAllRoles().includes(role.toUpperCase());
  };

  return (
    <AuthContext.Provider
      value={{
        loading: loginApi.loading || userApi.loading,
        currentUser,
        error: loginApi.error,
        login,
        logout,
        isAdmin,
        hasRole
      }}>
      {props.children}
    </AuthContext.Provider>
  );

};

export default AuthProvider;
