import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CRow, CInput, CSelect, CButton} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import researchApi from "../../api/researchApis";
import foodApi from "../../api/foodApi";
import SearchBox from "../../components/SearchBox";
import useForm from "../../services/UseForm";
import {DAYS, CATEGORIES, MEALS} from "../../const";
import Utils from "../../services/utils";
import {freeSet} from "@coreui/icons";

const FoodFreeCaseDetail = (props) => {
  const foodSearcher = foodApi.useSearchFood();
  const detailGetter = researchApi.useGetCaseDetailApi();
  const detailUpdater = researchApi.useUpdateCaseDetailApi();
  const detailDeleter = researchApi.useDeleteCaseDetailApi();
  const former = useForm();
  let {researchId, caseId} = useParams();
  let [foods, setFoods] = useState([]);
  let [adding, setAdding] = useState(false);
  let [details, setDetails] = useState([]);
  const [foodOptions,setOptions] = useState([]);
  const [unitOptions,setUnitOptions] = useState([]);

  let mealOptions = ()=> {
    return Object.keys(MEALS).map((key) => {
      return <option value={key}>{MEALS[key]}</option>;
    })
  }
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
          foodId: foodId, amount: former.values[foodId + '_' + days], days: 1
          , unitId: former.values[foodId + '_unit'] ? former.values[foodId + '_unit'] : 1
        }
      }).then();
  };
  const deleteCaseDetail = function (detailId) {
    detailDeleter.call({
      urlParams: {researchId: researchId, caseId: caseId, detailId: detailId},
    }).then((response)=> {
      fetchData();
    });
  };

  function clearForm() {
    former.setInput('new_food', null);
    Object.keys(DAYS).forEach((days)=>former.setInput('new_' + days, ''));
  }

  const createCase = function (days) {
    if (former.values['new_food'] && former.values['new_unit'])
      setAdding(true);
    detailUpdater.call({
      urlParams: {researchId: researchId, caseId: caseId},
      body: {
        foodId: former.values['new_food'].id, amount: former.values['new_' + days], days: days
        , unitId: former.values['new_unit'] ? former.values['new_unit'] : 1, meal: former.values['meal']
      }
    }).then(()=> {
      setAdding(false);
      fetchData();
      clearForm();
    }).catch(()=>setAdding(false));
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
      resp.sort((i1, i2)=> {
        var firstFood = i1.food.food;
        var secondFood = i2.food.food;
        return Utils.compareSrting(firstFood.category, secondFood.category) == 0 ?
          Utils.compareSrting(firstFood.name, secondFood.name) :
          Utils.compareSrting(firstFood.category, secondFood.category);
      })
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
      former.setDefault({'meal':Object.keys(MEALS)[0]})
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
              <CCol xs="6" lg="6">
                <CRow>
                  <CCol xs="6" lg="6"><b>ماده‌ی غذایی</b></CCol>
                  <CCol xs="6" lg="6"><b>وعده</b></CCol>
                </CRow>
              </CCol>
              <CCol xs="6" lg="6">
                <CRow>
                  <CCol xs="6" lg="6"><b>واحد</b></CCol>
                  <CCol xs="6" lg="6"><b>مصرف روزانه</b></CCol>
                </CRow>
              </CCol>
            </CRow>
            {details && details.map((detail, index) => {
                let food = detail.food.food;
                return <CRow className={(index % 2 == 0) ? "bg-gradient-success-2" : "bg-gradient-secondary-2"}>
                  <CCol xs="6" lg="6">
                    <CRow>
                      <CCol xs="6" lg="6">
                        {
                          CATEGORIES[food.category] ?
                            <CIcon content={freeSet[CATEGORIES[food.category].icon]}
                                   title={CATEGORIES[food.category].name}/>
                            :
                            <CIcon />
                        }<span style={{marginRight: "5px"}}>{food.name} </span>
                      </CCol>
                      <CCol xs="6" lg="6">
                        <span >{MEALS[detail.meal]} </span>
                      </CCol>

                    </CRow>
                  </CCol>
                  <CCol xs="6" lg="6">
                    <CRow>
                      <CCol xs="6" lg="6">
                        <span>{detail?.unit?.name}</span>
{/*                         <CSelect custom
                                 name={food.id + '_unit'}
                                 id={food.id + '_unit'}
                                 onBlur={()=>updateCaseUnit(food.id)}
                                 onChange={former.handle}
                        >
                          {options(details, food.unitUsages, food)}
                        </CSelect>
 */}                      </CCol>
                      <CCol xs="3" lg="3">
                        <span>
                          {defaultAmount(details, food.id, 1)}
                        </span>
{/*                         <CInput type="number"
                                name={food.id + '_1'}
                                onBlur={()=>updateCase(food.id, 1)}
                                onChange={former.handle}
                                defaultValue={defaultAmount(details, food.id, 1)}
                        />
 */}                      </CCol>
                      <CCol xs="2" lg="2">
                        <CButton
                          href={"#"}
                          color="danger"
                          variant="outline"
                          shape="square"
                          size="sm"
                          onClick={()=> {
                            deleteCaseDetail(detail.id);
                          }}
                        >
                          حذف
                        </CButton>
                      </CCol>
                    </CRow>
                  </CCol>
                </CRow>
              }
            )}
            <CRow>
              <CCol xs="6" lg="6">
                <CRow>
                  <CCol xs="6" lg="6">
                    <SearchBox
                      name="new_food"
                      options={foodOptions}
                      placeHolder="یک غذا انتخاب کنید"
                      onChange={former.handle}
                      fetch={fetchFoodOptions}
                    />
                  </CCol>
                  <CCol xs="6" lg="6">
                    <CSelect custom
                             name={'meal'}
                             id={'meal'}
                             onChange={former.handle}
                    >
                      {mealOptions()}
                    </CSelect>
                  </CCol>
                </CRow>
              </CCol>
              <CCol xs="6" lg="6">
                <CRow>
                  <CCol xs="6" lg="6">
                    <CSelect custom
                             name={'new_unit'}
                             id={'new_unit'}
                             onChange={former.handle}
                    >
                      {newFoodOptions(former.values['new_food'])}
                    </CSelect>
                  </CCol>
                  <CCol xs="3" lg="3">
                    <CInput type="number"
                            name={'new_1'}
                            id={'new_1'}
                            onChange={former.handle}
                            disabled={(adding) ? "disabled" : ""}
                    />
                  </CCol>
                  <CCol xs="2" lg="2">
                    <CButton
                      href={"#"}
                      color="success"
                      variant="outline"
                      shape="square"
                      size="sm"
                      onClick={()=> {
                        createCase(1);
                      }}
                    >
                      تایید
                    </CButton>
                  </CCol>

                </CRow>
              </CCol>
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
};

export default FoodFreeCaseDetail
