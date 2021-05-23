import React, {useEffect, useRef, useState, useContext} from "react";
import {
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CDataTable,
  CRow,
  CButton,
  CToast,
  CToastBody,
  CToaster,
  CToastHeader
} from "@coreui/react";
import useFetch from "../../services/UseFetch";
import {BASE_URL} from "../../const";
import JDate from "../../components/JDate";
import useModal from "../../services/useModal";
import NewResearchForm from "./NewResearchForm";


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

const ResearchList = (props) => {
  const r = useFetch({url: '/research'});

  useEffect(() => {
    r.call();

    // eslint-disable-next-line
  }, []);
  let refresh = ()=> {
    r.call();
  };
  const [showModal,Modal]=useModal({
    title: 'تحقیق جدید',
    body: ({close})=><NewResearchForm history={props.history}
                                      handleOk={()=> {
                                        close();
                                        refresh();
                                      }}></NewResearchForm>
  });
  return (
    <>
    <CRow>
      <CCol xs="12" lg="12">
        <CCard>
          <CCardHeader>
            لیست عنواین تحقیقاتی
            <CButton
              color="primary"
              variant="outline"
              shape="square"
              size="sm"
              onClick={showModal}
              className="float-right"
            >
              افزودن
            </CButton>
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
                'createDate': (item)=> (<td className="py-2">
                  <JDate date={item.createDate}></JDate>
                </td>),
                'detail': (item)=> (
                  <td className="py-2">
                    <CButton
                      href={"#/research/" + item.id + "/case"}
                      color="primary"
                      variant="outline"
                      shape="square"
                      size="sm"
                      className="mx-1"
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
                      className="mx-1"
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
    <Modal/>
    </>
  )
};

export default ResearchList
