import React, {useContext} from "react";
import {
  CWidgetDropdown,
  CRow,
  CCol,
  CWidgetProgressIcon,
  CDropdown,
  CProgress,
  CDropdownMenu,
  CDropdownItem,
  CDropdownToggle
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import ChartLineSimple from "../charts/ChartLineSimple";
import ChartBarSimple from "../charts/ChartBarSimple";
import {AuthContext} from "../../context/Auth/AuthProvider";
import utils from "../../services/utils";
const WidgetsDropdown = () => {
  const auth = useContext(AuthContext);
  console.log('cu', auth.currentUser);
  let diffInHour = utils.dateDiffHours(auth.currentUser.expiresAt, new Date().getTime());
  let barValue = diffInHour <= 0 ? 100 : diffInHour > 48 ? 5 : ((48 - diffInHour) * 100 / 48);
  console.log('current user=', auth.currentUser, auth.currentUser.expiresAt);
  return (<>
    <CRow>
      <CWidgetProgressIcon
        header={utils.jDate(auth.currentUser.expiresAt)}
        text={diffInHour > 0 ? "تا انقضای حساب شما " + utils.dateDiffStr(auth.currentUser.expiresAt, new Date().getTime()) + " مانده" : "حساب شما منقضی شده"}
        color="gradient-danger"
        progressSlot={
          <CProgress color={diffInHour <= 0 ? "danger" : diffInHour > 48 ? "gradient-primary" : "warning"} size="xs"
                     value={barValue} animated className="my-3"
          />}
      >
        <CIcon name="cil-speedometer" height="36"/>
      </CWidgetProgressIcon>

    </CRow>
    <CRow>
      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="gradient-primary"
          header="9.823"
          text="Members online"
          footerSlot={
            <ChartLineSimple
              pointed
              className="c-chart-wrapper mt-3 mx-3"
              style={{height: '70px'}}
              dataPoints={[65, 59, 84, 84, 51, 55, 40]}
              pointHoverBackgroundColor="primary"
              label="Members"
              labels="months"
            />
          }
        >
          <CDropdown>
            <CDropdownToggle color="transparent">
              <CIcon name="cil-settings"/>
            </CDropdownToggle>
            {/*
             <CDropdownMenu className="pt-0" placement="bottom-end">
             <CDropdownItem>Action</CDropdownItem>
             <CDropdownItem>Another action</CDropdownItem>
             <CDropdownItem>Something else here...</CDropdownItem>
             <CDropdownItem disabled>Disabled action</CDropdownItem>
             </CDropdownMenu>
             */}
          </CDropdown>
        </CWidgetDropdown>
      </CCol>

      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="gradient-info"
          header="9.823"
          text="Members online"
          footerSlot={
            <ChartLineSimple
              pointed
              className="mt-3 mx-3"
              style={{height: '70px'}}
              dataPoints={[1, 18, 9, 17, 34, 22, 11]}
              pointHoverBackgroundColor="info"
              options={{elements: {line: {tension: 0.00001}}}}
              label="Members"
              labels="months"
            />
          }
        >
          <CDropdown>
            <CDropdownToggle caret={false} color="transparent">
              <CIcon name="cil-location-pin"/>
            </CDropdownToggle>
            {/*
             <CDropdownMenu className="pt-0" placement="bottom-end">
             <CDropdownItem>Action</CDropdownItem>
             <CDropdownItem>Another action</CDropdownItem>
             <CDropdownItem>Something else here...</CDropdownItem>
             <CDropdownItem disabled>Disabled action</CDropdownItem>
             </CDropdownMenu>
             */}
          </CDropdown>
        </CWidgetDropdown>
      </CCol>

      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="gradient-warning"
          header="9.823"
          text="Members online"
          footerSlot={
            <ChartLineSimple
              className="mt-3"
              style={{height: '70px'}}
              backgroundColor="rgba(255,255,255,.2)"
              dataPoints={[78, 81, 80, 45, 34, 12, 40]}
              options={{elements: {line: {borderWidth: 2.5}}}}
              pointHoverBackgroundColor="warning"
              label="Members"
              labels="months"
            />
          }
        >
          <CDropdown>
            <CDropdownToggle color="transparent">
              <CIcon name="cil-settings"/>
            </CDropdownToggle>
            <CDropdownMenu className="pt-0" placement="bottom-end">
              <CDropdownItem>Action</CDropdownItem>
              <CDropdownItem>Another action</CDropdownItem>
              <CDropdownItem>Something else here...</CDropdownItem>
              <CDropdownItem disabled>Disabled action</CDropdownItem>
            </CDropdownMenu>
          </CDropdown>
        </CWidgetDropdown>
      </CCol>

      <CCol sm="6" lg="3">
        <CWidgetDropdown
          color="gradient-danger"
          header="9.823"
          text="Members online"
          footerSlot={
            <ChartBarSimple
              className="mt-3 mx-3"
              style={{height: '70px'}}
              backgroundColor="rgb(250, 152, 152)"
              label="Members"
              labels="months"
            />
          }
        >
          <CDropdown>
            <CDropdownToggle caret className="text-white" color="transparent">
              <CIcon name="cil-settings"/>
            </CDropdownToggle>
            <CDropdownMenu className="pt-0" placement="bottom-end">
              <CDropdownItem>Action</CDropdownItem>
              <CDropdownItem>Another action</CDropdownItem>
              <CDropdownItem>Something else here...</CDropdownItem>
              <CDropdownItem disabled>Disabled action</CDropdownItem>
            </CDropdownMenu>
          </CDropdown>
        </CWidgetDropdown>
      </CCol>
    </CRow>
    </>
  )
}

export default WidgetsDropdown
