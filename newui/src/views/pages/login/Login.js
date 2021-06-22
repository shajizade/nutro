import React, {useContext} from "react";
import {Link} from "react-router-dom";
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
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
import useForm from "../../../services/UseForm";
import {AuthContext} from "../../../context/Auth/AuthProvider";
const Login = (props) => {
  const former = useForm();
  const auth = useContext(AuthContext)
  const loginRequest = ()=> {
    auth.login(former.values).then(()=> {
        props.history.push("/");
      }
    );
  }
  return (



    <div className="c-app c-default-layout flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md="8">
            <CCardGroup>
              <CCard className="p-4">
                <CCardBody>
                  <CForm method="POST" action="/api/login">
                    <h1>ورود</h1>
                    <p className="text-muted"></p>
                    <CInputGroup className="mb-3">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-user"/>
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="text" placeholder="نام کاربری" autoComplete="username"
                              name="username"
                              onChange={former.handle}/>
                    </CInputGroup>
                    <CInputGroup className="mb-4">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-lock-locked"/>
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="password" placeholder="گذرواژه" autoComplete="current-password"
                              name="password"
                              onChange={former.handle}
                      />
                    </CInputGroup>
                    <CRow>
                      <CCol xs="6">
                        <CButton color="primary" className="px-4" onClick={loginRequest}>ورود</CButton>
                      </CCol>
                      <CCol xs="6" className="text-right">
                        <CButton color="link" className="px-0">گذرواژه را فراموش کرده‌اید؟</CButton>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <CCard className="text-white bg-primary py-5 d-md-down-none" style={{width: '44%'}}>
                <CCardBody className="text-center">
                  <div>
                    <h2>ثبت نام</h2>
                    <p>آیا دوست دارید ثبت نام کنید؟
                      پس فرصت را غنیمت بشمارید.</p>
                    <Link to="/register">
                      <CButton color="primary" className="mt-3" active tabIndex={-1}>ثبت نام کنید!</CButton>
                    </Link>
                  </div>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Login
