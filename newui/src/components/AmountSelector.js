import React from "react";
import {CSelect, CInput} from "@coreui/react";

const AmountSelector = (props) => {
  const options = ()=> {
    let result = [<option value="1">گرم</option>];
    if (props && props.units) {
      result.push(props.units.map((unitUsage) => {
        if (unitUsage && unitUsage.unit)
          return <option value={unitUsage.unit.id}
                         selected={props.defaultUnit == unitUsage.unit.id}>{unitUsage.unit.name}</option>;
      }));
    }
    return result;
  };

  return <>
  <CInput type="number" placeholder={props.placeholder}
          name={props.name}
          defaultValue={props.defaultAmount}
          onBlur={props.onBlur}
          onChange={props.onChange}
  />
  <CSelect custom
           name={props.name + '_unit'}
           id={props.name + '_unit'}
           onBlur={props.onBlur}
           onChange={props.onChange}>
    {options()}
  </CSelect>
  </>;
};
export default AmountSelector;
