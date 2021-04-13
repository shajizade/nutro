import useFetch from "../services/UseFetch";

export default {
  useUpdateRecipe: (options) => useFetch({
    url: `/food/{id}/ingredients`,
    method: 'put',
    contentType: 'application/json', ...options
  }),
  useGetIngredients: (options) => useFetch({
    url: `/food/{id}/ingredients`,
    method: 'get',
    contentType: 'application/json', ...options
  }),
  useSearchFood: (options) => useFetch({url: `/food/`, method: 'get', contentType: 'application/json', ...options}),
  useCalculateFood: (options) => useFetch({
    url: `/food/calculate`,
    method: 'put',
    contentType: 'application/json', ...options
  }),
  useGetFullFood: (options) => useFetch({
    url: `/food/{id}/full`,
    method: 'get',
    contentType: 'application/json', ...options
  }),
  useGetAllFoods: (options) => useFetch({url: `/food/`, method: 'get', contentType: 'application/json', ...options}),
  useCreateFood: (options) => useFetch({url: `/food/`, method: 'post', contentType: 'application/json', ...options}),
}
