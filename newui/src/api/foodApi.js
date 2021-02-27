import useFetch from "../services/UseFetch";

export default {
  useSearchFood: (options) => useFetch({url: `/food/`, method: 'get', contentType: 'application/json', ...options}),
  useCalculateFood: (options) => useFetch({
    url: `/food/calculate`,
    method: 'put',
    contentType: 'application/json', ...options
  }),
}
