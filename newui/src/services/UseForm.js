import React, {useState} from "react";

const useForm = ()=> {
  const [state, setState] = useState({values: {}, fields: []});
  const handle = (e)=> {
    var inputKey = e.target.name ? e.target.name : e.target.id;
    var inputValue = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    var newState = {
      values: {
        ...state.values,
        [inputKey]: inputValue
      }
    };
    setState(newState);
    return newState.values;
  };
  let former = (Component)=> {
    return (props)=><Component {...props} onChange={handle}/>;
  };
  const validate = ()=> {
    return true;
  }
  const setDefault = (defaultValues)=> {
    setState({
      values: defaultValues
    });

  }
  return {handle, values: state.values, validate: validate, former: former, setDefault: setDefault};
}
export default useForm;

