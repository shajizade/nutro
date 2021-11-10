import Moment from "moment-jalaali";

class Utils {
  static round(number, fraction) {
    if (number === 0)
      return 0;
    if (number && fraction)
      return (Math.round(number * (10 ^ fraction)) / (10 ^ fraction)).toFixed(fraction);
    if (number !== undefined)
      return Math.round(number);
    return null;
  }

  static compareSrting(s1, s2) {
    if (s1)
      return s1.localeCompare(s2);
    if (s2)
      return -1 * s2.localeCompare(s1);
    return 0;
  }

  static persianNumber(str) {
    return str.replace(/\d/g, d => '۰۱۲۳۴۵۶۷۸۹'[d]);
  }

  static jDate(date) {
    return Moment(new Date(date)).format('jYYYY/jM/jD').replace(/\d/g, d => '۰۱۲۳۴۵۶۷۸۹'[d]);
  }

  static dateDiffStr(date1, date2) {
    let diff = (date1 - date2) / 1000;
    if (diff < 60)
      return this.persianNumber(diff + ' ثانیه');
    if (diff < 60 * 60)
      return this.persianNumber(Math.round(diff / 60) + ' دقیقه');
    if (diff < 60 * 60 * 24)
      return this.persianNumber(Math.round(diff / 3600) + ' ساعت');
    return this.persianNumber(Math.round(diff / (3600 * 24)) + ' روز');
  }

  static dateDiffHours(date1, date2) {
    let diff = (date1 - date2) / 1000;
    let diffInHour = Math.round(diff / 3600);
    return diffInHour;
  }
}
export default Utils;
