import React from "react";
import { useFormik } from "formik";
import { useMutation } from "react-query";
import userService from "../../service/userService";

const Index = () => {
    const [register, { data }] = useMutation(userService.register);
    const formik = useFormik({
        initialValues: {
            email: "",
            password: "",
            confirmPassword: "",
        },
        onSubmit: (values) => {
            if (values.password !== values.confirmPassword) {
                formik.setFieldError("confirmPassword", "ridi");
            } else {
                register(values);
            }
        },
    });
    return (
        <div className={"container"}>
            <form onSubmit={formik.handleSubmit}>
                <label htmlFor={"email"}>Email</label>
                <input
                    id={"email"}
                    name={"email"}
                    value={formik.values.email}
                    onChange={formik.handleChange}
                />
                <label htmlFor={"password"}>password</label>
                <input
                    type={"password"}
                    id={"password"}
                    name={"password"}
                    value={formik.values.password}
                    onChange={formik.handleChange}
                />
                <label htmlFor={"confirmPassword"}>confirm password</label>
                <input
                    type={"password"}
                    id={"confirmPassword"}
                    name={"confirmPassword"}
                    value={formik.values.confirmPassword}
                    onChange={formik.handleChange}
                />
                <button type={"submit"}>register</button>
            </form>
        </div>
    );
};

export default Index;
