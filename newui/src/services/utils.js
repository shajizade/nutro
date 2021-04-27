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
}
export default Utils;
