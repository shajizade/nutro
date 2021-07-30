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
}
export default Utils;
