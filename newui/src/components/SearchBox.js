import React, {useState} from "react";
import Autosuggest from "react-autosuggest";

const SearchBox = (props) => {
  const getItemByValue = (value)=> {
    let search = props.options.filter(item =>
      item.name === value
    );
    return search ? search[0] : null;
  };
  const getLabelByValue = (value)=> {
    let item = getItemByValue(value);
    return item ? item.name : '';
  };
  const [innerValue,setInnerValue]=useState(props.initialValue ? getLabelByValue(props.initialValue) : '');
  // Use your imagination to render suggestions.
  const renderSuggestion = suggestion => (
    <div>
      {suggestion.name + " | " + (suggestion.itemNumber ? suggestion.itemNumber : suggestion.id)}
    </div>
  );
// Autosuggest will call this function every time you need to clear suggestions.
  const _onSuggestionsClearRequested = () => {
  };
  const getSuggestionValue = suggestion => suggestion.name;
  const onChange = (event, {newValue}) => {
    setInnerValue(newValue);
    var item = getItemByValue(newValue);
    console.log(newValue, item);
    if (item) {
      let e = {target: {name: props.name, value: item}};
      console.log(e);
      props.onChange(e);
    }
  };
  let inputProps = {
    placeholder: props.placeHolder,
    value: innerValue,
    onChange: onChange
  };
  return <Autosuggest
    suggestions={props.options}
    onSuggestionsFetchRequested={props.fetch}
    onSuggestionsClearRequested={_onSuggestionsClearRequested}
    getSuggestionValue={getSuggestionValue}
    renderSuggestion={renderSuggestion}
    inputProps={inputProps}
  />;
}

export default React.memo(SearchBox)
