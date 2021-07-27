import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput, CSelect} from "@coreui/react";
import researchApi from "../../api/researchApis";
import foodApi from "../../api/foodApi";
import SearchBox from "../../components/SearchBox";
import useForm from "../../services/UseForm";
import {DAYS} from "../../const";

const FoodFreeCaseDetail = (props) => {
  console.log('FOOOOOOOOOOOOOOD FREEEEEEEEEEEEEEEE');
  const foodSearcher = foodApi.useSearchFood();
  const detailGetter = researchApi.useGetCaseDetailApi();
  const detailUpdater = researchApi.useUpdateCaseDetailApi();
  const former = useForm();
  let {researchId, caseId} = useParams();
  let [foods, setFoods] = useState([]);
  let [details, setDetails] = useState([]);
  const [foodOptions,setOptions] = useState([]);
  const [unitOptions,setUnitOptions] = useState([]);

  const fetchFoodOptions = ({value}) => {
    foodSearcher.call({body: {name: value}})
      .then((resp)=> {
        setOptions(resp.content);
      });
  };


  const defaultAmount = function (details, foodId, days) {
    if (!details)
      return 0;
    let item = details.find(item=>item.food.id === foodId && item.days == days);
    return item && item.amount;
  };
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
  };
  const createCase = function (days) {
    if (former.values['new_food'] && former.values['new_unit'])
      detailUpdater.call({
        urlParams: {researchId: researchId, caseId: caseId},
        body: {
          foodId: former.values['new_food'].id, amount: former.values['new_' + days], days: days
          , unitId: former.values['new_unit'] ? former.values['new_unit'] : 1
        }
      }).then(()=>fetchData());
  };
  const updateCaseUnit = function (foodId) {
    var days = Object.keys(DAYS).find(days=>former.values[foodId + '_' + days] > 0);
    if (days)
      updateCase(foodId, days);
  };
  const options = (details, unitUsages, food)=> {
    let result = [<option value="1">گرم</option>];
    if (unitUsages) {
      result.push(unitUsages.map((unitUsage) => {
        if (unitUsage && unitUsage.unit)
          return <option value={unitUsage.unit.id}
                         selected={defaultUnit(details, food.id) == unitUsage.unit.id}>{unitUsage.unit.name}</option>;
      }));
    }
    return result;
  };
  const newFoodOptions = (food)=> {
    let result = [<option value="1">گرم</option>];
    if (food && food.unitUsages) {
      result.push(food.unitUsages.map((unitUsage) => {
        return <option value={unitUsage.unit.id}>{unitUsage.unit.name}</option>;
      }));
    }
    return result;
  };

  let fetchData = ()=> {
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
      former.setDefault({...defaultAmounts, ...defaultUnits, 'new_unit': 1});
      setFoods(resp.map(item=>item.food.food));
      //console.log('foods',resp);
    });
  }

  useEffect(() => {
    fetchData();
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
              <CCol xs="2" lg="2"><b>ماده‌ی غذایی</b></CCol>
              <CCol xs="2" lg="2"><b>واحد</b></CCol>
              <CCol xs="2" lg="2"><b>مصرف روزانه</b></CCol>
              <CCol xs="2" lg="2"><b>هفتگی</b></CCol>
              <CCol xs="2" lg="2"><b>ماهانه</b></CCol>
              <CCol xs="2" lg="2"><b>سالانه</b></CCol>
            </CRow>
            {foods && foods.map((food, index) =>
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
            <CRow>
              <CCol xs="2" lg="2">
                <SearchBox
                  name="new_food"
                  options={foodOptions}
                  placeHolder="یک غذا انتخاب کنید"
                  onChange={former.handle}
                  fetch={fetchFoodOptions}
                />
              </CCol>
              <CCol xs="2" lg="2">
                <CSelect custom
                         name={'new_unit'}
                         id={'new_unit'}
                         onChange={former.handle}
                >
                  {newFoodOptions(former.values['new_food'])}
                </CSelect>
              </CCol>
              {
                Object.keys(DAYS).map((days)=>
                  <CCol xs="2" lg="2">
                    <CInput type="number"
                            name={'new_' + days}
                            onBlur={()=>createCase(days)}
                            onChange={former.handle}
                    />
                  </CCol>
                )
              }
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
};

export default FoodFreeCaseDetail
