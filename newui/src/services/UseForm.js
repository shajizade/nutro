import React, {useState} from "react";

const useForm = ()=> {
  const [state, setState] = useState({values: {}, fields: []});

  function updateState(inputKey, inputValue) {
    var newState = {
      values: {
        ...state.values,
        [inputKey]: inputValue
      }
    };
    setState(newState);
    return newState.values;
  }

  const handle = (e)=> {
    var inputKey = e.target.name ? e.target.name : e.target.id;
    var inputValue = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    return updateState(inputKey, inputValue);
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
  const setInput = (id, value)=> {
    var element = document.getElementById(id);
    if (element) {
      element.value = value;
      updateState(id, value);
    }
  }
  return {handle, values: state.values, validate: validate, former: former, setDefault: setDefault, setInput: setInput};
}
export default useForm;

