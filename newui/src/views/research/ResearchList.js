import React, {useEffect} from "react";
import {CCard, CCardBody, CCardHeader, CCol, CDataTable, CRow, CButton} from "@coreui/react";
import useFetch from "../../services/UseFetch";
import {BASE_URL} from "../../const";


const fields = [
  {
    key: 'id',
    label: 'شناسه'
  },
  {
    key: 'name',
    label: 'عنوان تحقیق'
  },
  {
    key: 'createDate',
    label: 'تاریخ آغاز'
  },
  {
    key: 'detail',
    label: 'جزئیات'

  }
];

const ResearchList = () => {
  const r = useFetch({url: '/research'});

  useEffect(() => {
    r.call();
    // eslint-disable-next-line
  }, []);
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست عنواین تحقیقاتی
          </CCardHeader>
          <CCardBody>

            <CDataTable
              items={r && r.response && r.response.content}
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
                    <CButton
                      href={BASE_URL + "/research/" + item.id + "/excel"}
                      color="primary"
                      variant="outline"
                      shape="square"
                      size="sm"
                      onClick={()=> {
                      }}
                    >
                      ⬇️اکسل
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

export default ResearchList
