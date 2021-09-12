import React from "react";
import authApi from "../../../api/authApis";
import useForm from "../../../services/UseForm";
import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCol,
  CContainer,
  CForm,
  CInput,
  CInputGroup,
  CInputGroupPrepend,
  CInputGroupText,
  CRow
} from "@coreui/react";
import CIcon from "@coreui/icons-react";

const Register = () => {
  const registerer = authApi.useRegisterApi();
  const former = useForm();
  const register = ()=> {
    //console.log(former)
    registerer.call({
        body: {
          user: {username: former.values['username']}, password: former.values['password']
        }
      }
    ).then(()=>alert('done'));
  };
  return (
    <div className="c-app c-default-layout flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md="9" lg="7" xl="6">
            <CCard className="mx-4">
              <CCardBody className="p-4">
                <CForm>
                  <h1>ثبت نام</h1>
                  <p className="text-muted">در کمتر از یک دقیقه ثبت نام کنید!</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupPrepend>
                      <CInputGroupText>
                        <CIcon name="cil-user"/>
                      </CInputGroupText>
                    </CInputGroupPrepend>
                    <CInput name="username" id="username" onChange={former.handle}
                            type="text" placeholder="نام کاربری" autoComplete="username"/>
                  </CInputGroup>
                  {/*
                   <CInputGroup className="mb-3">
                   <CInputGroupPrepend>
                   <CInputGroupText>@</CInputGroupText>
                   </CInputGroupPrepend>
                   <CInput type="text" placeholder="Email" autoComplete="email"/>
                   </CInputGroup>
                   */}
                  <CInputGroup className="mb-3">
                    <CInputGroupPrepend>
                      <CInputGroupText>
                        <CIcon name="cil-lock-locked"/>
                      </CInputGroupText>
                    </CInputGroupPrepend>
                    <CInput name="password" id="password" onChange={former.handle}
                            type="password" placeholder="گذرواژه"/>
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupPrepend>
                      <CInputGroupText>
                        <CIcon name="cil-lock-locked"/>
                      </CInputGroupText>
                    </CInputGroupPrepend>
                    <CInput name="password2" id="password2" onChange={former.handle}
                            type="password" placeholder="تکرار گذرواژه"/>
                  </CInputGroup>
                  <CButton color="success" block onClick={register}>ثبت نام</CButton>
                </CForm>
              </CCardBody>
              {/*
               <CCardFooter className="p-4">
               <CRow>
               <CCol xs="12" sm="6">
               <CButton className="btn-facebook mb-1" block><span>facebook</span></CButton>
               </CCol>
               <CCol xs="12" sm="6">
               <CButton className="btn-twitter mb-1" block><span>twitter</span></CButton>
               </CCol>
               </CRow>
               </CCardFooter>
               */}
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Register
