import React from "react";
import {CCardBody, CListGroup, CListGroupItem, CBadge} from "@coreui/react";

const FoodNutrriotionTable = (props) => {
  let sort = (a, b)=> {
    if (a.nutrition.main && !b.nutrition.main)
      return -1;
    if (!a.nutrition.main && b.nutrition.main)
      return 1;
    return a.nutrition.name.localeCompare(b.nutrition.name);
  }
  let nutritions = (props.nutritions ? props.nutritions : []).sort(sort);

  return <CCardBody>
    <CListGroup>
      {nutritions.map((item)=>
        <CListGroupItem action accent={item.nutrition.main ? 'info' : 'success'}
                        color={item.nutrition.main ? 'info' : 'success'}>
          {item.nutrition.name}
          <span className="float-right">{item.amount} <CBadge color="warning">{item.nutrition.unit.name}</CBadge></span>
        </CListGroupItem>)}
    </CListGroup>
  </CCardBody>;
}

export default React.memo(FoodNutrriotionTable)
