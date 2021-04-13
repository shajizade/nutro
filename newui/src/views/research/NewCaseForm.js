import React from "react";
import {CCard, CButton, CCardBody, CFormGroup, CLabel, CInput, CCol, CRow, CSelect} from "@coreui/react";
import ResearchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";

const NewCaseForm = (props) => {
  console.log(props);
  const caseCreator = ResearchApi.useAddCaseApi();
  const createCase = ()=> {
    caseCreator.call({body: former.values, urlParams: {researchId: props.researchId}}).then(
      (resp)=> {
        if (props.handleOk) {
          props.handleOk()
        }
        ;
      }
    );
  }
  const former = useForm();
  return (
    <>
    <CRow>
      <CCol xs="12" sm="12">
        <CCard>
          <CCardBody>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="name">نام و نام خانوادگی</CLabel>
                  <CInput id="name" placeholder="مثلا سعید حاجی‌زاده" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="code">کد</CLabel>
                  <CInput id="code" placeholder="مثلا 10245" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="age">سن</CLabel>
                  <CInput id="age" type="number" placeholder="مثلا 29" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="gender">جنسیت</CLabel>
                  <CSelect custom name="gender" id="gender" onChange={former.handle}>
                    <option value="MALE">مرد</option>
                    <option value="FEMALE">زن</option>
                  </CSelect>

                </CFormGroup>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <CRow>
      <CButton color="primary" className="px-4 pull-left" onClick={createCase}>ثبت</CButton>
    </CRow>
    </>
  )
};

export default NewCaseForm
