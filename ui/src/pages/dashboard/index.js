import React, { useState } from "react";
import { useQuery } from "react-query";
import foodService from "../../service/foodService";
import { FieldArray, FormikProvider, useFormik } from "formik";
import { TextField } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Autocomplete from "@material-ui/lab/Autocomplete";
import Grid from "@material-ui/core/Grid";
import IconButton from "@material-ui/core/IconButton";
import { Add, Close } from "@material-ui/icons";
import Card from "@material-ui/core/Card";
import Box from "@material-ui/core/Box";
import CardHeader from "@material-ui/core/CardHeader";

const Index = () => {
    const [search, setSearch] = useState({});
    const { data: foods, isLoading } = useQuery(["foods", { name: search.name }], foodService.getFoods);
    const formik = useFormik({
        initialValues: {
            rows: [{ food: null, gram: "" }],
        },
        onSubmit: (values) => foodService.calculate(values.rows, 100),
    });
    return (
        <div className={"container page"}>
            <form onSubmit={formik.handleSubmit}>
                <FormikProvider value={formik}>
                    <FieldArray
                        name="rows"
                        render={(arrayHelpers) => (
                            <>
                                {formik.values.rows.map((food, index) => (
                                    <Box mb={2}>
                                        <Card>
                                            <CardHeader
                                                action={
                                                    <IconButton color="secondary" component="span" onClick={() => arrayHelpers.remove(index)}>
                                                        <Close />
                                                    </IconButton>
                                                }
                                            />
                                            <Box p={5} pt={0}>
                                                <Grid container justify={"space-around"} key={index}>
                                                    <Grid item xs={10} md={5}>
                                                        {" "}
                                                        <Autocomplete
                                                            fullWidth
                                                            options={foods?.content || []}
                                                            getOptionLabel={(option) => option.name}
                                                            name={`rows.${index}.food`}
                                                            loading={isLoading}
                                                            renderInput={(params) => <TextField {...params} label="غذا" />}
                                                            value={formik.values.rows[index].food}
                                                            onChange={(_, val) => {
                                                                setSearch("");
                                                                formik.setFieldValue(`rows.${index}.food`, val);
                                                            }}
                                                            onInputChange={(e, v) => setSearch({ name: v || "" })}
                                                            getOptionSelected={(option, value) => option.id === value.id}
                                                        />
                                                    </Grid>
                                                    <Grid item xs={10} md={5}>
                                                        {" "}
                                                        <TextField
                                                            label={"مقدار"}
                                                            fullWidth
                                                            type={"number"}
                                                            name={`rows.${index}.gram`}
                                                            value={formik.values.rows[index].gram}
                                                            onChange={formik.handleChange}
                                                        />
                                                    </Grid>
                                                </Grid>
                                            </Box>
                                        </Card>
                                    </Box>
                                ))}
                                <Box align={"left"} my={5}>
                                    <Button
                                        startIcon={<Add />}
                                        variant={"contained"}
                                        onClick={() => {
                                            arrayHelpers.push({
                                                food: null,
                                                gram: "",
                                            });
                                        }}
                                    >
                                        افزودن ردیف
                                    </Button>
                                </Box>
                                <Box align={"center"}>
                                    <Button size={"large"} color={"primary"} fullWidth variant={"contained"} type="submit">
                                        محاسبه
                                    </Button>
                                </Box>
                            </>
                        )}
                    />
                </FormikProvider>
            </form>
        </div>
    );
};

export default Index;
