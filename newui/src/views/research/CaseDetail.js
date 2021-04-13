import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput} from "@coreui/react";
import researchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";

const CaseDetail = (props) => {
  const foodsGetter = researchApi.useGetResearchFoodsApi();
  const detailGetter = researchApi.useGetCaseDetailApi();
  const detailUpdater = researchApi.useUpdateCaseDetailApi();
  const former = useForm();
  let {researchId, caseId} = useParams();
  let [foods, setFoods] = useState([]);
  let [details, setDetails] = useState([]);
  const updateCase = function (foodId, days) {
    if (foodId && former.values[days + '_' + foodId])
      detailUpdater.call({
        urlParams: {researchId: researchId, caseId: caseId},
        body: {foodId: foodId, amount: former.values[days + '_' + foodId], days: days, unitId: 1}
      }).then();
  }

  useEffect(() => {
    foodsGetter.call({urlParams: {id: researchId}}).then(resp=>setFoods(resp));
    detailGetter.call({urlParams: {researchId: researchId, caseId: caseId}}).then(resp=> {
      setDetails(resp);
      console.log('det', resp)
    });
    // eslint-disable-next-line
  }, []);
  let defaultAmount = function (details, foodId, days) {
    if (!details)
      return 0;
    let item = details.find(item=>item.food.id === foodId && item.days === days);
    return item && item.amount;
  }
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست موارد {caseId}
          </CCardHeader>
          <CCardBody>
            {foods && foods.map(food =>
              <CRow><CCol xs="3" lg="3"> {food.name} </CCol>
                <CCol xs="3" lg="3">
                  <CInput type="number" placeholder="مقدار روزانه"
                          name={'1_' + food.id}
                          defaultValue={defaultAmount(details, food.id, 1)}
                          onBlur={()=>updateCase(food.id, 1)}
                          onChange={former.handle}
                  />
                </CCol>
                <CCol xs="3" lg="3">
                  <CInput type="number" placeholder="مقدار هفتگی"
                          name={'7_' + food.id}
                          defaultValue={defaultAmount(details, food.id, 7)}
                          onBlur={()=>updateCase(food.id, 7)}
                          onChange={former.handle}
                  />
                </CCol>
                <CCol xs="3" lg="3">
                  <CInput type="number" placeholder="مقدار ماهانه"
                          name={'30_' + food.id}
                          defaultValue={defaultAmount(details, food.id, 30)}
                          onBlur={()=>updateCase(food.id, 30)}
                          onChange={former.handle}
                  />
                </CCol>
              </CRow>
            )}
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
};

export default CaseDetail
