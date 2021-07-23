import React, {useEffect, useContext} from "react";
import useStorage from "../../services/UseStorage";

export const StorageContext = React.createContext();

const StorageProvider = (props) => {
  let [state,setState] = useStorage('state', {});
  let get = (key)=> {
    return state[key];
  };
  let set = (key, value)=> {
    setState(...state, {key: value});
  };
  return (
    <StorageContext.Provider
      value={{
        get,
        set
      }}>
      {props.children}
    </StorageContext.Provider>
  );

};

export default StorageProvider;
