import React, {Suspense} from "react";
import {BrowserRouter, Switch} from "react-router-dom";
import {Route} from "react-router";
import ProtectedRoute from "./components/ProtectedRoute";
import "./app.scss";
import Layout from "./components/layout";
import AppProvider from "./context";
import Dashboard from "./pages/dashboard";
import Register from "./pages/register";
import Login from "./pages/login";

/*
const Dashboard = React.lazy(() => import("./pages/dashboard"));
const Register = React.lazy(() => import("./pages/register"));
const Login = React.lazy(() => import("./pages/login"));
 */

function App() {
    return (
        <BrowserRouter>
            <AppProvider>
                <Layout>
                    <Suspense fallback={"Loading..."}>
                        <Switch>
                            <ProtectedRoute
                                path={"/"}
                                exact
                                component={Dashboard}
                            />
                            <Route path={"/login"} exact component={Login} />
                            <Route
                                path={"/register"}
                                exact
                                component={Register}
                            />
                        </Switch>
                    </Suspense>
                </Layout>
            </AppProvider>
        </BrowserRouter>
    );
}

export default App;
