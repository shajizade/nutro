import React, {useEffect, useState} from "react";
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
  CToastHeader,
  CToastClose
} from "@coreui/react";
export const AlertContext = React.createContext();

const AlertProvider = (props) => {

  const [toasts,setToasts] =useState([]);
  const addToast = (body, title, type = 'success')=> {
    setToasts([
      ...toasts,
      {title, body, type}
    ])
  }
  return (
    <AlertContext.Provider
      value={{
        addToast
      }}>
      {props.children}
      <CToaster position="top-right">
        {toasts.map(toast=><CToast
          className={"bg-gradient-" + toast.type + " color-white"}
          show={true}
          autohide={3000}
          fade={true}
        >
          {toast.title &&
          <CToastHeader className="bg-transparent color-white">
            {toast.title}
          </CToastHeader>
          }
          <CToastBody close>
            {toast.body}
          </CToastBody>
        </CToast>)}
      </CToaster>
    </AlertContext.Provider>
  );

};

export default AlertProvider;
