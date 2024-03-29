import React, {useContext} from "react";
import {CDropdown, CDropdownItem, CDropdownMenu, CDropdownToggle, CImg} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {AuthContext} from "../context/Auth/AuthProvider";
import {useHistory} from "react-router-dom";


const TheHeaderDropdown = () => {
  const auth = useContext(AuthContext);
  const history = useHistory();

  return (
    <CDropdown
      inNav
      className="c-header-nav-items mx-2"
      direction="down"
    >
      <CDropdownToggle className="c-header-nav-link" caret={false}>
        <div className="c-avatar">
          <CImg
            src={'avatars/6.jpg'}
            className="c-avatar-img"
            alt="admin@bootstrapmaster.com"
          />
        </div>
      </CDropdownToggle>
      <CDropdownMenu className="pt-0" placement="bottom-end">
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          <strong>{auth.currentUser && (auth.currentUser.name ? auth.currentUser.name : auth.currentUser.username)}</strong>
        </CDropdownItem>
        <CDropdownItem onClick={()=> {
          auth.logout().then(()=> {
            history.push("/");
          })
        }}>
          <CIcon name="cil-lock-locked" className="mfe-2"/>
          خروج
        </CDropdownItem>
      </CDropdownMenu>
    </CDropdown>
  )
}

export default TheHeaderDropdown
