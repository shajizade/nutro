import React from "react";
import AuthProvider from "./authProvider";
import ReactQueryProvider from "./reactQueryProvider";
import ThemeProvider from "./themeProvider";

const AppProvider = (props) => {
    return (
        <ReactQueryProvider>
            <ThemeProvider>
                <AuthProvider>{props.children}</AuthProvider>
            </ThemeProvider>
        </ReactQueryProvider>
    );
};

export default AppProvider;
