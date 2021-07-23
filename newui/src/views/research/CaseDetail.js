import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput, CSelect} from "@coreui/react";
import SimpleCaseDetail from "./SimpleCaseDetail";
import FoodFreeCaseDetail from "./FoodFreeCaseDetail";


const CaseDetail = (props) => {
  let {foodFree} = useParams();
  return (foodFree ?
      <FoodFreeCaseDetail>
      </FoodFreeCaseDetail>
      :
      <SimpleCaseDetail>
      </SimpleCaseDetail>
  );
};

export default CaseDetail
