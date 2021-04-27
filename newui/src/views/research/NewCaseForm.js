import React, {useState} from "react";
import {CCard, CButton, CCardBody, CFormGroup, CLabel, CInput, CCol, CRow, CSelect} from "@coreui/react";
import ResearchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";
import Utils from "../../services/utils";

const NewCaseForm = (props) => {
  console.log(props);
  const caseCreator = ResearchApi.useAddCaseApi();
  let bmi = ()=> {
    if (former.values.weight && former.values.height)
      return Utils.round(former.values.weight / ((former.values.height / 100.0) * (former.values.height / 100.0)), 2);
    return '';
  };
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
  const Age = former.former(<CInput id="age" type="number" placeholder="مثلا 29" required/>);
  return (
    <>
    <CRow>
      <CCol xs="12" sm="12">
        <CCard>
          <CCardBody>
            <CRow>
              <CCol xs="12" sm="8">
                <CFormGroup>
                  <CLabel htmlFor="name">نام و نام خانوادگی</CLabel>
                  <CInput id="name" placeholder="مثلا سعید حاجی‌زاده" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="code">کد</CLabel>
                  <CInput id="code" placeholder="مثلا 10245" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="age">سن</CLabel>
                  <Age/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="8">
                <CFormGroup>
                  <CLabel htmlFor="gender">جنسیت</CLabel>
                  <CSelect custom name="gender" id="gender" onChange={former.handle}>
                    <option value=""></option>
                    <option value="MALE">مرد</option>
                    <option value="FEMALE">زن</option>
                    <option value="PREG">زن باردار</option>
                    <option value="LACT">زن شیرده</option>
                  </CSelect>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="height">قد</CLabel>
                  <CInput id="height" type="number" step="0.5" placeholder="مثلا 175" required
                          onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="weight">وزن</CLabel>
                  <CInput id="weight" type="number" step="0.5" placeholder="مثلا 65.5" required
                          onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="bmi">BMI</CLabel>
                  <CInput disabled value={bmi()}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="waist">دور کمر</CLabel>
                  <CInput id="waist" type="number" step="0.5" placeholder="مثلا 65.5" required
                          onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="hip">دور باسن</CLabel>
                  <CInput id="hip" type="number" step="0.5" placeholder="مثلا 65.5" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="4">
                <CFormGroup>
                  <CLabel htmlFor="hip">کمر/باسن</CLabel>
                  <CInput disabled value={Utils.round(former.values.waist / former.values.hip, 2)}/>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="6">
                <CFormGroup>
                  <CLabel htmlFor="activity">فعالیت</CLabel>
                  <CSelect custom name="activity" id="activity" onChange={former.handle}>
                    <option value="25">۲۵٪ (صحبت کردن ، تلویزیون دیدن و ...)</option>
                    <option value="30">۳۰٪(نشستن ، مطالعه کردن)</option>
                    <option value="55">۵۵٪(مطالعه کردن ، تایپ کردن و ...)</option>
                    <option value="65">۶۵٪ (باغ بانی و ...)</option>
                    <option value="75">۷۵٪ (دوچرخه سواری ، اسکی و ...)</option>
                    <option value="85">۸۵٪ (دویدن ، ورزشکاران)</option>
                    <option value="100">۱۰۰٪ (فوتبال ، چاله کندن و ...)</option>
                    <option value="120">۱۲۰٪ (ساخت‌وساز، حمل بار)</option>
                    <option value="130">۱۳۰٪ (۸ ساعت کار سخت در روز)</option>
                  </CSelect>
                </CFormGroup>
              </CCol>
              <CCol xs="12" sm="6">
                <CFormGroup>
                  <CLabel htmlFor="sickness">بیماری</CLabel>
                  <CInput id="sickness" placeholder="مثلا دیابت" required onChange={former.handle}/>
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
