import React from "react";
import {CCard, CButton, CCardBody, CFormGroup, CLabel, CInputCheckbox, CInput, CCol, CRow} from "@coreui/react";
import FoodApi from "../../api/foodApi";
import useForm from "../../services/UseForm";

const NewFoodForm = (props) => {
  console.log(props);
  const foodCreator = FoodApi.useCreateFood();
  const createFood = ()=> {
    foodCreator.call({body: former.values}).then(
      (resp)=> {
        if (resp.recipe)
          props.history.push("/admin/updateRecipe/" + resp.id);
        else if (props.handleOk) {
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
                  <CLabel htmlFor="name">نام غذا</CLabel>
                  <CInput id="name" placeholder="مثلا نخود پخته" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="abbreviation">عنوان</CLabel>
                  <CInput id="abbreviation" placeholder="مثلا boiled pace" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup>
                  <CLabel htmlFor="itemNumber">شماره</CLabel>
                  <CInput id="itemNumber" placeholder="مثلا 134562" required onChange={former.handle}/>
                </CFormGroup>
              </CCol>
            </CRow>
            <CRow>
              <CCol xs="12">
                <CFormGroup variant="custom-checkbox" inline>
                  <CInputCheckbox custom id="recipe" name="recipe" onChange={former.handle}/>
                  <CLabel variant="custom-checkbox" htmlFor="recipe">دستور غذای ترکیبی</CLabel>
                </CFormGroup>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <CRow>
      <CButton color="primary" className="px-4 pull-left" onClick={createFood}>ثبت</CButton>
    </CRow>
    </>
  )
};

export default NewFoodForm
