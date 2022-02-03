import React, { useState, useEffect } from "react";
import { CCard, CButton, CCardBody, CFormGroup, CLabel, CInput, CCol, CRow, CSelect } from "@coreui/react";
import ResearchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";

const NewResearchForm = (props) => {
  const former = useForm();
  const researchCreator = ResearchApi.useCreateResearchApi();
  const researchTypeGetter = ResearchApi.useGetResearchTypes();
  const [types, setTypes] = useState([]);
  useEffect(() => {
    researchTypeGetter.call()
      .then((resp) => {
        setTypes(resp);
        former.setDefault({'researchType':resp[0].id});
      });
    // eslint-disable-next-line
  }, []);

  const createResearch = () => {
    var research = Object.assign({}, former.values);
    research['researchType'] = { id: former.values['researchType'] };
    researchCreator.call({ body: research }).then(
      (resp) => {
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
                    <CInput id="name" placeholder="مثلا تحقیق fqm دانش آموزان" required onChange={former.handle} />
                  </CFormGroup>
                </CCol>
                <CCol xs="12" sm="8">
                  <CFormGroup>
                    <CLabel htmlFor="researchType">نوع تحقیق</CLabel>
                    <CSelect custom name="researchType" id="researchType" onChange={former.handle}>
                      {types.map(type =>
                        <option value={type.id}>{type.title}</option>
                      )}
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
