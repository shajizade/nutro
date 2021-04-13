import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CDataTable, CRow, CButton} from "@coreui/react";
import researchApi from "../../api/researchApis";
import useModal from "../../services/useModal";
import NewCaseForm from "./NewCaseForm";


const fields = [
  {
    key: 'code',
    label: 'کد'
  },
  {
    key: 'name',
    label: 'نام'
  },
  {
    key: 'gender',
    label: 'جنسیت'
  },
  {
    key: 'registerDate',
    label: 'تاریخ ثبت'

  },
  {
    key: 'detail',
    label: 'جزئیات'

  }
];

const CaseList = (props) => {
  //console.log('props',props.match.params);
  const r = researchApi.useGetCaseesApi();
  let {researchId} = useParams();
  const [showModal,Modal]=useModal({
    title: 'کیس جدید',
    body: ({handleOk})=><NewCaseForm history={props.history} handleOk={handleOk} researchId={researchId}></NewCaseForm>
  });
  useEffect(() => {
    r.call({urlParams: {id: researchId}});
    // eslint-disable-next-line
  }, []);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست موارد
            <CButton
              color="primary"
              variant="outline"
              shape="square"
              size="sm"
              onClick={showModal}
            >
              افزودن
            </CButton> </CCardHeader>
          <CCardBody>

            <CDataTable
              items={r.response && r.response.content}
              fields={fields}
              hover
              striped
              bordered
              size="sm"
              itemsPerPage={r.response ? r.response.pageable.pageSize : 1}
              pagination
              scopedSlots={{
                'detail': (item)=> (
                  <td className="py-2">
                    <CButton
                      href={"#/research/" + researchId + "/case/" + item.id + "/detail"}
                      color="primary"
                      variant="outline"
                      shape="square"
                      size="sm"
                      onClick={()=> {
                      }}
                    >
                      جزئیات
                    </CButton>
                  </td>
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

export default CaseList
