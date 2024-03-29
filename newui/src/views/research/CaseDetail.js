import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput, CSelect} from "@coreui/react";
import SimpleCaseDetail from "./SimpleCaseDetail";
import FoodFreeCaseDetail from "./FoodFreeCaseDetail";
import {getParams} from "../../services/QueryString";


const CaseDetail = (props) => {
  let params = getParams(props);
  console.log('params food free', params);
  return (params['foodFree'] === 'true' ?
      <FoodFreeCaseDetail>
      </FoodFreeCaseDetail>
      :
      <SimpleCaseDetail>
      </SimpleCaseDetail>
  );
};

export default CaseDetail
