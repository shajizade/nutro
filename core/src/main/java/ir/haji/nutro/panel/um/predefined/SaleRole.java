package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class SaleRole extends Role {

    public static final String NAME = "SALE";
    public static final String CAPTION = "فروش";

    public SaleRole() {
        super(NAME, CAPTION, new AdministrationRole());
    }

}
