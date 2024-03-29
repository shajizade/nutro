import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import {CCard, CCardBody, CCardHeader, CCol, CDataTable, CRow, CButton} from "@coreui/react";
import researchApi from "../../api/researchApis";
import useModal from "../../services/useModal";
import NewCaseForm from "./NewCaseForm";
import Translator from "../../services/Translator";
import JDate from "../../components/JDate";
import Gender from "../../components/Gender";


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
    key: 'status',
    label: 'وضعیت'
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
  const casesGetter = researchApi.useGetCaseesApi();
  const caseToggeler = researchApi.useCaseToggleApi();
  let {researchId} = useParams();

  function refresh() {
    casesGetter.call({urlParams: {id: researchId}});
  }
  const [showModal,Modal]=useModal({
    title: 'کیس جدید',
    body: ({close})=><NewCaseForm history={props.history} handleOk={()=> {
      close();
      refresh();
    }} researchId={researchId}></NewCaseForm>
  });
  useEffect(() => {
    refresh();
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
              className="float-right"
            >
              افزودن
            </CButton> </CCardHeader>
          <CCardBody>

            <CDataTable
              items={casesGetter.response && casesGetter.response.content}
              fields={fields}
              hover
              striped
              bordered
              size="sm"
              itemsPerPage={casesGetter.response ? casesGetter.response.pageable.pageSize : 1}
              pagination
              scopedSlots={{
                'registerDate': (item)=> (<td className="py-2">
                  <JDate date={item.registerDate}></JDate>
                </td>),
                'gender': (item)=> (<td className="py-2">
                  <Gender gender={item.gender}></Gender>
                </td>),
                'detail': (item)=> (
                  <td className="py-2">
                    <CButton
                      href={"#/research/" + researchId + "/case/" + item.id + "/detail?foodFree=" + item.foodFree}
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
                ),
                'status': (item)=> (
                  <td className="py-2">
                    <CButton
                      href="#"
                      color="primary"
                      variant="outline"
                      shape="square"
                      size="sm"
                      onClick={()=> {
                        console.log(item);
                        caseToggeler.call({urlParams: {researchId: item.researchId, caseId: item.id}})
                          .then(setTimeout(()=>casesGetter.call({urlParams: {id: researchId}}), 500));
                      }}
                    >
                      {Translator(item.status)}
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
