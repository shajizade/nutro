import useFetch from "../services/UseFetch";

export default {
  useGetResearchesApi: (options) => useFetch({url: `/research`, method: 'get', ...options}),
  useGetCaseesApi: (options) => useFetch({url: '/research/{id}/case', method: 'get', ...options})
}
