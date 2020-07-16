package ir.haji.nutro.util;

/**
 * Created by Saeed on 10/29/2016.
 */
public class DefaultSortByIdSpecificationUtil extends SpecificationUtil {
    @Override
    public String getDefaultSort() {
        return "id,desc";
    }
}