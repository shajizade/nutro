import React from "react";
import utils from "../services/utils";
const JDate = (props) => {
  return <span>
    {utils.jDate(props.date)}
   </span>;
}

export default React.memo(JDate)
