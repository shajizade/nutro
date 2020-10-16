import React from "react";
import { ThemeProvider as MaterialThemeProvider } from "@material-ui/core/styles";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import { create } from "jss";
import rtl from "jss-rtl";
import { StylesProvider, jssPreset } from "@material-ui/core/styles";
import { faIR } from "@material-ui/core/locale";

const jss = create({ plugins: [...jssPreset().plugins, rtl()] });

const theme = createMuiTheme(
    {
        direction: "rtl",
        typography: {
            fontFamily: "IranSans",
        },
    },
    faIR
);

const ThemeProvider = (props) => {
    return (
        <MaterialThemeProvider theme={theme}>
            <StylesProvider jss={jss}>{props.children}</StylesProvider>
        </MaterialThemeProvider>
    );
};

export default ThemeProvider;
