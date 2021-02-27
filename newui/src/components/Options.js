import React from "react";
const Options = (props) => {
  return props.items.map((item)=>
    <option value={item.value}>
      {item.label ? item.label : item.value}
    </option>
  );
}

export default React.memo(Options)
