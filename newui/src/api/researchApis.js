import useFetch from "../services/UseFetch";

export default {
  useGetResearchesApi: (options) => useFetch({url: `/research`, method: 'get', ...options}),
  useGetCaseesApi: (options) => useFetch({url: '/research/{id}/case', method: 'get', ...options}),
  useGetResearchFoodsApi: (options) => useFetch({url: '/research/{id}/foods', method: 'get', ...options}),
  useGetCaseDetailApi: (options) => useFetch({
    url: '/research/{researchId}/case/{caseId}/detail',
    method: 'get', ...options
  }),
  useUpdateCaseDetailApi: (options) => useFetch({
    url: '/research/{researchId}/case/{caseId}/detail',
    method: 'post', ...options
  }),
  useAddCaseApi: (options) => useFetch({url: '/research/{researchId}/case/', method: 'post', ...options}),
  useCaseToggleApi: (options) => useFetch({
    url: '/research/{researchId}/case/{caseId}/toggle',
    method: 'put', ...options
  })
}
