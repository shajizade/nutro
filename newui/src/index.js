import "react-app-polyfill/ie11";
import "react-app-polyfill/stable";
import "./polyfill";
import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import {icons} from "./assets/icons";
import {Provider} from "react-redux";
import store from "./store";
import AuthProvider from "./context/Auth/AuthProvider";
import AlertProvider from "./context/AlertProvider";
import StorageProvider from "./context/Storage/StorageProvider";

React.icons = icons

ReactDOM.render(
  <Provider store={store}>
    <AlertProvider>
      <AuthProvider>
        <StorageProvider>
          <App/>
        </StorageProvider>
      </AuthProvider>
    </AlertProvider>
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
