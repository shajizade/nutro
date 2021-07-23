import React, {useMemo, useState} from "react";
import {CModal, CModalHeader, CModalBody, CModalFooter} from "@coreui/react";

const useModal = ({body: Body, title: Title}) => {

  const [state, setState] = useState({
    visible: false,
    data: null
  });

  const showModal = (data) => {
    setState({data: data, visible: true});
  };

  const close = () => {
    setState({...state, visible: false});
  };



  const render = () => (
    <CModal
      show={state.visible}
      onClose={close}
    >
      <CModalHeader closeButton>{typeof(Title).toString() == 'string' ? Title : <Title/>}</CModalHeader>
      <CModalBody>
        <Body data={state.data} close={close}/>
      </CModalBody>
      <CModalFooter>
        {/*
        <CButton color="primary" onClick={handleOk}>تایید</CButton>{' '}
        <CButton
          color="secondary"
          onClick={handleCancel}
        >بستن</CButton>
         */}
      </CModalFooter>
    </CModal>
  );
  // eslint-disable-next-line
  return useMemo(() => [showModal, render], [state.visible])
};

export default useModal;
