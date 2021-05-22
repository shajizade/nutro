import React from "react";
import Moment from "moment-jalaali";
const JDate = (props) => {
  return <span>
    {Moment(new Date(props.date)).format('jYYYY/jM/jD').replace(/\d/g, d => '۰۱۲۳۴۵۶۷۸۹'[d])}
   </span>;
}

export default React.memo(JDate)
