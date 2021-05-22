import React, {useState} from "react";
import {CCard, CButton, CCardBody, CFormGroup, CLabel, CInput, CCol, CRow, CSelect} from "@coreui/react";
import ResearchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";

const NewResearchForm = (props) => {
  const former = useForm();
  const researchCreator = ResearchApi.useCreateResearchApi();
  const createResearch = ()=> {
    researchCreator.call({body: former.values}).then(
      (resp)=> {
        if (props.handleOk) props.handleOk()
      }
    );
  };
  return (
    <>
    <CRow>
      <CCol xs="12" sm="12">
        <CCard>
          <CCardBody>
            <CRow>
              <CCol xs="12" sm="8">
                <CFormGroup>
                  <CLabel htmlFor="name">عنوان تحقیق</CLabel>
                  <CInput id="name" placeholder="مثلا تحقیق fqm دانش آموزان" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="8">
                <CFormGroup>
                  <CLabel htmlFor="gender">نوع تحقیق</CLabel>
                  <CSelect custom name="gender" id="gender" onChange={former.handle}>
                    <option value=""></option>
                    <option value="fqm">fqm</option>
                    <option value="24hours">۲۴ ساعته</option>
                  </CSelect>
                </CFormGroup>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <CRow>
      <CCol xs="12" sm="12">
        <CButton color="primary" className="px-4 float-right" onClick={createResearch}>ثبت</CButton>
      </CCol>
    </CRow>
    </>
  )
};

export default NewResearchForm
