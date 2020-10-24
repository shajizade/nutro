package ir.haji.nutro.util;

import org.hashids.Hashids;

/**
 * Created by saeed on 10/23/2020.
 */
public class HashUtil {
    private static Hashids caseHash = new Hashids("HashIdsSaltCase", 10);

    public static String getCaseCode(Long id) {
        return caseHash.encode(id);
    }

    public static Long getCaseId(String hashCode) {
        long[] decode = caseHash.decode(hashCode);
        if (decode == null || decode.length != 1)
            return null;
        return decode[0];
    }
}
