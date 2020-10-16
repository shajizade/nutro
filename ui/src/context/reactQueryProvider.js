import React from "react";
import { QueryCache, ReactQueryCacheProvider } from "react-query";

const ReactQueryProvider = (props) => {
    const queryCache = new QueryCache({
        defaultConfig: {
            queries: {
                staleTime: Infinity,
                retry: false,
                refetchOnWindowFocus: false,
            },
        },
    });

    return (
        <ReactQueryCacheProvider queryCache={queryCache}>
            {props.children}
        </ReactQueryCacheProvider>
    );
};

export default ReactQueryProvider;
