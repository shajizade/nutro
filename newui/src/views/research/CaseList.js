import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CDataTable, CRow, CButton} from "@coreui/react";
import researchApi from "../../api/researchApis";


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
  console.log(researchId);
  // eslint-disable-next-line
  useEffect(() => {
    r.call({urlParams: {id: researchId}});
  }, []);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست موارد
          </CCardHeader>
          <CCardBody>

            <CDataTable
              items={r.response ?.content}
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
                      href={"#/research/" + item.id + "/case"}
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
    </>
  )
};

export default CaseList
