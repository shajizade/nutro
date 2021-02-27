import useFetch from "../services/UseFetch";

export default {
  useLoginApi: (options) => useFetch({url: `/login`, method: 'post', contentType: 'form-data', ...options}),
  useUserApi: (options) => useFetch({url: '/user/me', method: 'get', ...options}),
  useLogoutApi: (options) => useFetch({url: '/logout', method: 'get', ...options})
}
