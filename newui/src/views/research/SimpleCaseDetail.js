import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput, CSelect, CButton} from "@coreui/react";
import researchApi from "../../api/researchApis";
import useForm from "../../services/UseForm";
import {DAYS} from "../../const";
import SearchBox from "../../components/SearchBox";

const SimpleCaseDetail = (props) => {
  const foodsGetter = researchApi.useGetResearchFoodsApi();
  const detailGetter = researchApi.useGetCaseDetailApi();
  const detailUpdater = researchApi.useUpdateCaseDetailApi();
  const former = useForm();
  let {researchId, caseId, foodFree} = useParams();
  let [foods, setFoods] = useState([]);
  let [showingFoods, setShowingFoods] = useState([]);
  let [details, setDetails] = useState([]);

  const defaultAmount = function (details, foodId, days) {
    if (!details)
      return 0;
    let item = details.find(item=>item.food.id === foodId && item.days == days);
    return item && item.amount;
  };
  const changeFilter = function(element){
    const filterToken = element.target.value;
    console.log('filter',filterToken)
    setShowingFoods(foods.filter(food => food.name.includes(filterToken)))
  }
  const clearFilter = () => {
      setShowingFoods(foods)
      former.setInput('filter','')
  }
  const defaultUnit = function (details, foodId) {
    if (!details)
      return 1;
    let item = details.find(item=>item.food.id === foodId);
    if (item && item.unit)
      return item.unit.id;
    return 1;
  };
  const updateCase = function (foodId, days) {
    if (foodId && former.values[foodId + '_' + days])
      detailUpdater.call({
        urlParams: {researchId: researchId, caseId: caseId},
        body: {
          foodId: foodId, amount: former.values[foodId + '_' + days], days: days
          , unitId: former.values[foodId + '_unit'] ? former.values[foodId + '_unit'] : 1
        }
      }).then();
  }
  const updateCaseUnit = function (foodId) {
    var days = Object.keys(DAYS).find(days=>former.values[foodId + '_' + days] > 0);
    console.log(foodId, former.values, days);
    if (days)
      updateCase(foodId, days);
  }
  const options = (details, unitUsages, food)=> {
    let result = [<option value="1" >گرم</option>];
    if (unitUsages) {
      result.push(unitUsages.map((unitUsage) => {
        if (unitUsage && unitUsage.unit)
          return <option value={unitUsage.unit.id} 
                         selected={defaultUnit(details, food.id) == unitUsage.unit.id}>{unitUsage.unit.name}</option>;
      }));
    }
    return result;
  };
  useEffect(() => {
    foodsGetter.call({urlParams: {id: researchId}}).then(resp=>{setFoods(resp);setShowingFoods(resp)});
    detailGetter.call({urlParams: {researchId: researchId, caseId: caseId}}).then(resp=> {
      setDetails(resp);
      let defaultAmounts = resp.reduce(function (map, obj) {
        map[obj.food.id + '_' + obj.days] = obj.amount;
        return map;
      }, {});
      let defaultUnits = resp.reduce(function (map, obj) {
        map[obj.food.id + '_unit'] = obj.unit.id;
        return map;
      }, {});
      former.setDefault({...defaultAmounts, ...defaultUnits});
    });
    // eslint-disable-next-line
  }, []);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست موارد {caseId}
          </CCardHeader>
          <CCardBody>
            <CRow>
              <CCol xs="12" lg="2">فیلتر</CCol>
              <CCol xs="12" lg="8">
                <CInput type="text"
                                name='filter'
                                id='filter'
                                onChange={changeFilter}
                        />                
              </CCol>
              <CCol xs="12" lg="2">
                <CButton
                  color="primary"
                  variant="outline"
                  shape="square"
                  size="sm"
                  onClick={clearFilter}
                  className="float-right"
                >
                  حذف فیلتر
                </CButton>
              </CCol>              
            </CRow>
            <hr></hr>
            <CRow>
              <CCol xs="2" lg="2"><b>ماده‌ی غذایی</b></CCol>
              <CCol xs="2" lg="2"><b>واحد</b></CCol>
              <CCol xs="2" lg="2"><b>مصرف روزانه</b></CCol>
              <CCol xs="2" lg="2"><b>هفتگی</b></CCol>
              <CCol xs="2" lg="2"><b>ماهانه</b></CCol>
              <CCol xs="2" lg="2"><b>سالانه</b></CCol>
            </CRow>
            {showingFoods && showingFoods.map((food, index) =>
              <CRow className={(index % 2 == 0) ? "bg-gradient-success-2" : "bg-gradient-secondary-2"}>
                <CCol xs="2" lg="2"> {food.name} </CCol>
                <CCol xs="2" lg="2">
                  <CSelect custom
                           name={food.id + '_unit'}
                           id={food.id + '_unit'}
                           onBlur={()=>updateCaseUnit(food.id)}
                           onChange={former.handle}
                  >
                    {options(details, food.unitUsages, food)}
                  </CSelect>
                </CCol>
                {
                  Object.keys(DAYS).map((days)=>
                    <CCol xs="2" lg="2">
                      <CInput type="number"
                              name={food.id + '_' + days}
                              onBlur={()=>updateCase(food.id, days)}
                              onChange={former.handle}
                              defaultValue={defaultAmount(details, food.id, days)}
                      />
                    </CCol>
                  )
                }
              </CRow>
            )}
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
};

export default SimpleCaseDetail
