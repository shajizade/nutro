import {useState} from "react";
import {BASE_URL} from "../const";
const defaultOptions = {method: 'get', contentType: 'application/json'};
function BadRequest(response) {
  this.response = response;
}
const queryParamSerializer = function (obj, prefix) {
  var str = [],
    p;
  for (p in obj) {
    if (obj.hasOwnProperty(p)) {
      var k = prefix ? prefix + "[" + p + "]" : p,
        v = obj[p];
      str.push((v !== null && typeof v === "object") ?
        queryParamSerializer(v, k) :
      encodeURIComponent(k) + "=" + encodeURIComponent(v));
    }
  }
  return str.join("&");
}
const useFetch = (options)=> {
  options = {...defaultOptions, ...options};
  const [state, setState] = useState({
    response: null,
    error: false,
    loading: false,
  });
  const call = (newOptions)=> {
    let finalOptions = {...options, ...newOptions};
    finalOptions.headers = finalOptions.headers ? finalOptions.headers : [];
    if (finalOptions.contentType) {
      if (finalOptions.contentType === 'application/json' && finalOptions.body && finalOptions.method !== 'get') {
        finalOptions.body = JSON.stringify(finalOptions.body);
      }
      if (finalOptions.contentType === 'form-data' && finalOptions.body) {
        var newBody = new FormData();
        for (const key in finalOptions.body) {
          newBody.append(key, finalOptions.body[key]);
        }
        finalOptions.body = newBody;
        finalOptions.contentType = undefined;
      }
      if (finalOptions.contentType)
        finalOptions.headers = new Headers({'content-type': finalOptions.contentType});
    }
    if (finalOptions.method === 'get' && finalOptions.body) {
      let queryParams = queryParamSerializer(finalOptions.body);
      finalOptions.body = undefined;
      finalOptions.url = finalOptions.url + '?' + queryParams;
    }
    if (finalOptions.queryParams && finalOptions.queryParams.length > 0) {
      finalOptions.url = finalOptions.url + "?"
        + finalOptions.queryParams.map((param)=> {
          return param.name + "=" + param.value
        }).join("&");
    }
    setState({response: null, loading: true, error: false});
    if (finalOptions.urlParams) {
      for (const key in finalOptions.urlParams) {
        finalOptions.url = finalOptions.url.replace("{" + key + "}", finalOptions.urlParams[key]);
      }
    }
    finalOptions.mode = 'cors';
    finalOptions.credentials = 'include';

    console.log(finalOptions);
    return fetch(BASE_URL + finalOptions.url, finalOptions)
      .then(resp=> {
          if (resp.status > 299 || resp.status < 200) {
            throw new BadRequest(resp);
          }
          else {
            const contentType = resp.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1)
              return resp.json();
            else
              return resp.text();
          }
        }
      )
      .then(resp => {
        console.log("fetch", resp);
        setState({response: resp, loading: false, error: false});
        return resp;
      })
      .catch(ex => {
        console.error('usefetach exceptionsetState', ex);
        setState({response: ex, loading: false, error: true});
        throw ex;
      });
  };

  return {call, response: state.response, loading: state.loading, error: state.error};
}
export default useFetch;
