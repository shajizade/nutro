import React, {useContext} from "react";
import {useSelector, useDispatch} from "react-redux";
import {
  CCreateElement,
  CSidebar,
  CSidebarBrand,
  CSidebarNav,
  CSidebarNavDivider,
  CSidebarNavTitle,
  CSidebarMinimizer,
  CSidebarNavDropdown,
  CSidebarNavItem
} from "@coreui/react";
import navigation from "./_nav";
import {AuthContext} from "../context/Auth/AuthProvider";

// sidebar nav config


const TheSidebar = () => {
  const auth = useContext(AuthContext);
  const dispatch = useDispatch()
  const show = useSelector(state => state.sidebarShow);
  let filterByRole = (items)=> {
    if (!auth.isAdmin()) {
      return items.filter(item=>!item.adminOnly);
    }
    return items;
  }
  return (
    <CSidebar
      show={show}
      onShowChange={(val) => dispatch({type: 'set', sidebarShow: val})}
    >

      <CSidebarBrand className="d-md-down-none" to="/">
        Nutro
      </CSidebarBrand>
      <CSidebarNav>

        <CCreateElement
          items={filterByRole(navigation)}
          components={{
            CSidebarNavDivider,
            CSidebarNavDropdown,
            CSidebarNavItem,
            CSidebarNavTitle
          }}
        />
      </CSidebarNav>
      <CSidebarMinimizer className="c-d-md-down-none"/>
    </CSidebar>
  )
}

export default React.memo(TheSidebar)
