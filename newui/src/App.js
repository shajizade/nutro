import React, {Component} from "react";
import {HashRouter, Route, Switch} from "react-router-dom";
import TheLayout from "./containers/TheLayout";
import Login from "./views/pages/login/Login";
import Register from "./views/pages/register/Register";
import Page404 from "./views/pages/page404/Page404";
import Page500 from "./views/pages/page500/Page500";
import "./scss/style.scss";

const loading = (
  <div className="pt-3 text-center">
    <div className="sk-spinner sk-spinner-pulse"></div>
  </div>
)

// Containers
/*const TheLayout = React.lazy(() =>  ('./containers/TheLayout'));*/

// Pages
/*
const Login = React.lazy(() =>
  ('./views/pages/login/Login'));
const Register = React.lazy(() =>
  ('./views/pages/register/Register'));
const Page404 = React.lazy(() =>
  ('./views/pages/page404/Page404'));
const Page500 = React.lazy(() =>
  ('./views/pages/page500/Page500'));
 */

class App extends Component {

  render() {
    console.log('app');
    return (
      <HashRouter>
        <React.Suspense fallback={loading}>
          <Switch>
            <Route exact path="/login" name="Login Page" render={props => <Login {...props}/>}/>
            <Route exact path="/register" name="Register Page" render={props => <Register {...props}/>}/>
            <Route exact path="/404" name="Page 404" render={props => <Page404 {...props}/>}/>
            <Route exact path="/500" name="Page 500" render={props => <Page500 {...props}/>}/>
            <Route path="/" name="Home" render={props => <TheLayout {...props}/>}/>
          </Switch>
        </React.Suspense>
      </HashRouter>
    );
  }
}

export default App;
