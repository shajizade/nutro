import React, {useState, useEffect} from "react";
import {CCard, CInput, CCardBody, CCardHeader, CButton, CCol, CDataTable, CRow} from "@coreui/react";
import {useParams} from "react-router-dom";
import SearchBox from "../../components/SearchBox";
import foodApi from "../../api/foodApi";
import useForm from "../../services/UseForm";
import FoodNutritionTable from "../../components/FoodNutritionTable";


const fields = [
  {
    key: 'name',
    label: 'غذا'
  },
  {
    key: 'amount',
    label: 'مقدار'

  },
  {
    key: 'delete',
    label: 'حذف'

  }
];


const FoodCalculate = () => {
  const {foodId} = useParams();
  const foodIngredientsApi = foodApi.useGetIngredients();
  const foodSearcher = foodApi.useSearchFood();
  const fullFoodGetter = foodApi.useGetFullFood();
  const recipeUpdater = foodApi.useUpdateRecipe();
  const [options,setOptions] = useState([{value: 1, label: 'یک'}, {value: 2, label: 'دو'}]);
  const [foods,setFoods] = useState([]);
  const [baseFood,setBaseFood] = useState();
  const [nuts,setNuts] = useState([]);
  const former = useForm();
  useEffect(() => {
    foodSearcher.call().then((resp)=> {
      setOptions(resp.content);
    });
    foodIngredientsApi.call({urlParams: {id: foodId}}).then((resp)=> {
      setFoods(resp.sort((a, b)=>b.amount - a.amount).map((item)=> {
        return {amount: Math.round(item.amount * 100) / 100, food: item.food.food};
      }));
    });
    getFullFood();
    // eslint-disable-next-line
  }, []);

  const addFood = ()=> {
    setFoods(foods.concat(former.values));
  };

  const getFullFood = ()=> {
    fullFoodGetter.call(
      {
        urlParams: {id: foodId},
      }).then(resp=> {
      setNuts(resp.nutritions);
      setBaseFood(resp.food);
    });
  };
  const deleteFood = (item)=> {
    console.log(item);
    setFoods(foods.filter((row)=>row.food.id !== item.food.id));
  };
  const updateRecipe = ()=> {
    recipeUpdater.call(
      {
        urlParams: {id: foodId},
        body: foods.map((item)=> {
          return {gram: item.amount, foodId: item.food.id}
        })
      }).then((resp)=> {
      console.log('sucess', resp);
    });
  }
  const fetchOptions = ({value}) => {
    foodSearcher.call({body: {name: value}})
      .then((resp)=> {
        console.log(resp);
        setOptions(resp.content);
      });
  };
  console.log("rerender, foods:", foods);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            دستور غذا
            {baseFood && " | " + baseFood.name}
            <CButton color="primary" className="px-4 pull-left" onClick={updateRecipe}>ثبت</CButton>
          </CCardHeader>
          <CCardBody>
            <CRow>
              <CCol xs="4" lg="4">
                <SearchBox
                  name="food"
                  options={options}
                  placeHolder="یک غذا انتخاب کنید"
                  onChange={former.handle}
                  fetch={fetchOptions}
                />
              </CCol>
              <CCol xs="4" lg="4">
                <CInput type="number" placeholder="مقدار"
                        name="amount"
                        onChange={former.handle}/>
              </CCol>
              <CCol xs="4" lg="4">
                <CButton color="primary" className="px-4" onClick={addFood}>افزودن</CButton>
              </CCol>
            </CRow>
            <CRow>
              <CDataTable
                items={foods}
                fields={fields}
                hover
                striped
                bordered
                size="sm"
                itemsPerPage={20}
                pagination
                scopedSlots={{
                  'name': (item)=> (
                    <td className="py-2">
                      {item.food.name}
                    </td>
                  )
                  , 'delete': (item)=> (
                    <td className="py-2">
                      <CButton
                        color="primary"
                        variant="outline"
                        shape="square"
                        size="sm"
                        onClick={()=> {
                          deleteFood(item);
                        }}
                      >
                        حذف
                      </CButton>
                    </td>
                  )
                }}
              />
            </CRow>
            <CRow>
              <CButton
                color="primary"
                variant="outline"
                shape="square"
                size="sm"
                onClick={getFullFood}
              >
                جدول تغذیه‌ای
              </CButton>
            </CRow>
            <CRow>
              {nuts && <FoodNutritionTable nutritions={nuts}/>}
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
}

export default FoodCalculate
