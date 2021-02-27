import {useState} from "react";
const useForm = ()=> {
  const [state, setState] = useState({values: {}});
  const handle = (e)=> {
    setState({
      values: {...state.values, [e.target.name]: e.target.value}
    });
  };
  return {handle, values: state.values}
}
export default useForm;
