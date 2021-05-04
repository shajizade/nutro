const dictionary = {
  "ACCEPTED": "✅تایید شده",
  "CREATED": "❌تایید نشده",
};
const Translator = (word) => {
  return dictionary[word];
};
export default Translator;
