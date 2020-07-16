package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class FinancialRole extends Role {

    public static final String NAME = "FINANCIAL";
    public static final String CAPTION = "مالی";

    public FinancialRole() {
        super(NAME, CAPTION, new AdministrationRole());
    }

}
