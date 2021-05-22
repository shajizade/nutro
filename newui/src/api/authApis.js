import useFetch from "../services/UseFetch";

export default {
  useLoginApi: (options) => useFetch({
    url: `/log`,
    method: 'post',
    muteNotification: true,
    contentType: 'form-data', ...options
  }),
  useUserApi: (options) => useFetch({url: '/user/me', method: 'get', muteNotification: true, ...options}),
  useLogoutApi: (options) => useFetch({url: '/logout', method: 'get', ...options})
}
