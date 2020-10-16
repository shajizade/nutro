import React, { useContext } from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import { AuthContext } from "../../context/authProvider";
import { Grid } from "@material-ui/core";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

const Index = () => {
    const { login, loading } = useContext(AuthContext);
    const formik = useFormik({
        initialValues: {
            username: "",
            password: "",
        },
        validationSchema: yup.object({
            username: yup.string().required("الزامی"),
            password: yup.string().required("الزامی"),
        }),
        onSubmit: login,
    });
    return (
        <Container maxWidth={"xs"}>
            <Grid container direction={"column"} spacing={10}>
                <Grid item>
                    {" "}
                    <Typography variant={"h1"} align={"center"}>
                        ورود
                    </Typography>
                </Grid>
                <Grid item>
                    <form onSubmit={formik.handleSubmit}>
                        <Grid container direction={"column"} spacing={5}>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label={"نام کاربری"}
                                    name={"username"}
                                    id={"username"}
                                    value={formik.values["username"]}
                                    onChange={formik.handleChange}
                                    error={formik.errors.username}
                                    variant={"outlined"}
                                    size={"small"}
                                />
                            </Grid>
                            <Grid item>
                                <TextField
                                    fullWidth
                                    label={"رمز عبور"}
                                    type={"password"}
                                    name={"password"}
                                    id={"password"}
                                    value={formik.values["password"]}
                                    onChange={formik.handleChange}
                                    error={formik.errors.password}
                                    variant={"outlined"}
                                    size={"small"}
                                />
                            </Grid>
                            <Grid item>
                                <Button disabled={loading} variant={"contained"} size={"large"} color={"primary"} type={"submit"} fullWidth>
                                    ورود
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Index;
