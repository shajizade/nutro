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
  //const history=useHistory();
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
      .catch((resp)=> {
        setCurrentUser(null);
      });
  };

  const login = (user) => {
    console.log(user);
    return loginApi.call({body: user})
      .then((resp)=> {
        setCurrentUser(resp);
      }).catch(ex=> {
        alert.addToast('گذرنامه یا نام کاربری به درستی وارد نشده است', 'خطا', 'warning');
      });
  };


  const logout = () => {
    // delete_cookie("SESSION");
    logoutApi.call()
      .then(() => {
        getUser()
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
  const isAdmin = getAllRoles().includes("ADMINISTRATION");
  const isSystemAdmin = getAllRoles().includes("ADMIN");

  const hasRole = (role) => {
    return getAllRoles().includes(role.toUpperCase());
  };
  console.log('auth', currentUser);
  return (
    <AuthContext.Provider
      value={{
        loading: loginApi.loading || userApi.loading,
        currentUser,
        error: loginApi.error,
        login,
        logout,
        isAdmin,
        hasRole,
        isSystemAdmin
      }}>
      {props.children}
    </AuthContext.Provider>
  );

};

export default AuthProvider;
