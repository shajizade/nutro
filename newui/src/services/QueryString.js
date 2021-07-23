const dictionary = {
  "ACCEPTED": "✅تایید شده",
  "CREATED": "❌تایید نشده",
};
export const getParams = (props) => {
  let qs = props.location.search;
  qs = qs.substring(1);
  let params = qs.split('&');
  let result = {};
  params.forEach(item => {
    let kv = item.split('=');
    result[kv[0]] = kv[1];
  });
  return result;
};
