import React, { useContext } from "react";
import { AuthContext } from "../../context/authProvider";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import Button from "@material-ui/core/Button";
import makeStyles from "@material-ui/core/styles/makeStyles";
import MenuIcon from "@material-ui/icons/Menu";
import Grid from "@material-ui/core/Grid";
import Container from "@material-ui/core/Container";
import { Typography } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        minHeight: "100vh",
    },
    menuButton: {
        marginLeft: theme.spacing(2),
    },
    toolbar: {
        justifyContent: "space-between",
    },
    footer: {
        marginTop: "auto",
        backgroundColor: theme.palette.primary.main,
        color: theme.palette.primary.contrastText,
        padding: theme.spacing(1),
    },
}));
const Layout = (props) => {
    const { user, logout } = useContext(AuthContext);

    const classes = useStyles();
    return (
        <Grid container direction={"column"} className={classes.root}>
            <Grid item>
                <AppBar position="static">
                    <Toolbar className={classes.toolbar}>
                        <IconButton edge="start" color="inherit" aria-label="menu" className={classes.menuButton}>
                            <MenuIcon />
                        </IconButton>
                        <Grid container item justify={"flex-end"}>
                            <Grid item>
                                <Button color="inherit" onClick={logout}>
                                    logout
                                </Button>
                            </Grid>
                            {user && (
                                <Grid item>
                                    <span>{user.name}</span>
                                </Grid>
                            )}
                        </Grid>
                    </Toolbar>
                </AppBar>
            </Grid>
            <Grid item>
                <main>{props.children}</main>
            </Grid>
            <Grid item className={classes.footer}>
                <footer>
                    <Typography variant={"subtitle1"} align={"center"}>
                        تمامی حقوق این سایت متعلق به من است
                    </Typography>
                </footer>
            </Grid>
        </Grid>
    );
};

export default Layout;
