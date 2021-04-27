import React, {useState} from "react";

const useForm = ()=> {
  const [state, setState] = useState({values: {}, fields: []});
  const handle = (e)=> {
    setState({
      values: {
        ...state.values,
        [e.target.name ? e.target.name : e.target.id]: e.target.type === 'checkbox' ? e.target.checked : e.target.value
      }

    });
  };
  let former = (Component)=> {
    return (props)=><Component {...props} onChange={handle}/>;
  };
  const validate = ()=> {
    return true;
  }
  return {handle, values: state.values, validate: validate, former: former}
}
export default useForm;

