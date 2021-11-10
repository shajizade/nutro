import React, {lazy} from "react";
import {
  CBadge,
  CButton,
  CButtonGroup,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CCol,
  CProgress,
  CRow,
  CCallout
} from "@coreui/react";
import WidgetsDropdown from "../widgets/WidgetsDropdown";

/*
const WidgetsDropdown = lazy(() =>
  ('../widgets/WidgetsDropdown.js'))
const WidgetsBrand = lazy(() =>
  ('../widgets/WidgetsBrand.js'))
 */

const Dashboard = () => {
  return (
    <>
    <WidgetsDropdown />
    </>
  )
}

export default Dashboard
