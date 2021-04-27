import React, {useEffect} from "react";
import {CCard, CCardBody, CCardHeader, CCol, CDataTable, CRow, CButton} from "@coreui/react";
import FoodApi from "../../api/foodApi";
import useModal from "../../services/useModal";
import NewFoodForm from "./NewFoodForm";


const fields = [
  {
    key: 'id',
    label: 'شناسه',
    filter: false
  },
  {
    key: 'name',
    label: 'نام غذا',
    filter: true
  },
  {
    key: 'abbreviation',
    label: 'عنوان',
    filter: true
  },
  {
    key: 'itemNumber',
    label: 'شماره',
    filter: true
  },
  {
    key: 'operation',
    label: 'عملیات',
    filter: false

  }
];

const FoodList = (props) => {
  const foodGetter = FoodApi.useGetAllFoods();
  const [showModal,Modal]=useModal({
    title: 'غذای جدید',
    body: ({handleOk})=><NewFoodForm history={props.history} handleOk={handleOk}></NewFoodForm>
  });

  useEffect(() => {
    foodGetter.call();
    // eslint-disable-next-line
  }, []);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست غذاها
            <CButton color="primary" className="px-4 pull-left" onClick={showModal}>غذای جدید</CButton>
          </CCardHeader>
          <CCardBody>

            <CDataTable
              items={foodGetter && foodGetter.response && foodGetter.response.content}
              fields={fields}
              hover
              striped
              columnFilter
              bordered
              size="sm"
              itemsPerPage={foodGetter.response ? foodGetter.response.pageable.pageSize : 1}
              onColumnFilterChange={(c)=> {
                foodGetter.call({body: c})
              }}
              pagination
              loading={foodGetter && foodGetter.loading}
              scopedSlots={{
                'operation': (item)=> (
                  item.recipe ?
                    <td className="py-2">
                      <CButton
                        href={"#/admin/updateRecipe/" + item.id }
                        color="primary"
                        variant="outline"
                        shape="square"
                        size="sm"
                        onClick={()=> {
                        }}
                      >
                        ویرایش
                      </CButton>
                    </td>
                    : <span></span>
                )
              }}
            />
          </CCardBody>
        </CCard>
      </CCol>
    </CRow>
    <Modal/>
    </>
  )
};

export default FoodList
