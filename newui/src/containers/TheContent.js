import React, {Suspense} from "react";
import {Redirect, Route, Switch} from "react-router-dom";
import {CContainer, CFade} from "@coreui/react";
import ProtectedRoute from "../services/ProtectedRoute";
import routes from "../routes";

const loading = (
  <div className="pt-3 text-center">
    <div className="sk-spinner sk-spinner-pulse"></div>
  </div>
)

const TheContent = () => {
  return (
    <main className="c-main">
      <CContainer fluid>
        <Suspense fallback={loading}>
          <Switch>
            {routes.map((route, idx) => {
              return route.component && (
                  (route.isPublic ?
                      <Route
                        key={idx}
                        path={route.path}
                        exact={route.exact}
                        name={route.name}
                        render={props => (
                          <CFade>
                            <route.component {...props} />
                          </CFade>
                        )}/>
                      :
                      <ProtectedRoute
                        key={idx}
                        path={route.path}
                        exact={route.exact}
                        name={route.name}
                        component={props => (
                          <CFade>
                            <route.component {...props} />
                          </CFade>
                        )}/>
                  )
                )
            })}
            <Redirect from="/" to="/dashboard"/>
          </Switch>
        </Suspense>
      </CContainer>
    </main>
  )
}

export default React.memo(TheContent)
