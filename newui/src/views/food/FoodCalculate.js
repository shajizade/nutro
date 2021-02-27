import React, {useState, useEffect} from "react";
import {CCard, CInput, CCardBody, CCardHeader, CButton, CCol, CDataTable, CRow} from "@coreui/react";
import SearchBox from "../../components/SearchBox";
import foodApi from "../../api/foodApi";
import useForm from "../../services/UseForm";


const fields = [
  {
    key: 'name',
    label: 'عنوان تحقیق'
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
const nutFields = [
  {
    key: 'name',
    label: 'عنوان تحقیق'
  },
  {
    key: 'amount',
    label: 'مقدار'

  },
  {
    key: 'unit',
    label: 'واحد'

  }
];


const FoodCalculate = () => {
  const foodSearcher = foodApi.useSearchFood();
  const foodCalculator = foodApi.useCalculateFood();
  const [options,setOptions] = useState([{value: 1, label: 'یک'}, {value: 2, label: 'دو'}]);
  const [foods,setFoods] = useState([]);
  const [nuts,setNuts] = useState([]);
  const former = useForm();
  // eslint-disable-next-line
  useEffect(() => {
    foodSearcher.call().then((resp)=> {
      setOptions(resp.content);
    })
  }, []);

  const addFood = ()=> {
    setFoods(foods.concat(former.values));
  };
  const calculate = ()=> {
    foodCalculator.call(
      {
        body: foods.map((item)=> {
          return {gram: item.amount, number: item.food.itemNumber}
        }), queryParams: [{name: "portionGrams", value: former.values.portionAmount}]
      }).then(resp=>setNuts(resp));
  };
  const deleteFood = (item)=> {
    console.log(item);
    setFoods(foods.filter((row)=>row.food.id !== item.food.id));
  };
  const fetchOptions = ({value}) => {
    foodSearcher.call({body: {name: value}})
      .then((resp)=> {
        console.log(resp);
        setOptions(resp.content);
      });
  };
  console.log("rerender, foods:", foods && foods.length)
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            فرم محاسبه
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
              <CInput type="number" placeholder="وزن هر پرس"
                      name="portionAmount"
                      onChange={former.handle}/>

              <CButton
                color="primary"
                variant="outline"
                shape="square"
                size="sm"
                onClick={calculate}
              >
                محاسبه
              </CButton>
            </CRow>
            <CRow>
              {nuts && <CDataTable
                items={nuts}
                fields={nutFields}
                hover
                striped
                bordered
                size="sm"
                itemsPerPage={200}
                pagination
                scopedSlots={{
                  'name': (item)=> (
                    <td className="py-2">
                      {item.nutrition.name}
                    </td>
                  ),
                  'unit': (item)=> (
                    <td className="py-2">
                      {item.nutrition.unit.name}
                    </td>
                  )
                }}
              />}
            </CRow>
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    </>
  )
}

export default FoodCalculate
