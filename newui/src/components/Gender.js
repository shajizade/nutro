import React from "react";
import {GENDER} from "../const";


const Gender = (props) => {
  return <span>
    {props.gender && GENDER[props.gender] ? GENDER[props.gender] : "-"}
   </span>;
}

export default React.memo(Gender)
