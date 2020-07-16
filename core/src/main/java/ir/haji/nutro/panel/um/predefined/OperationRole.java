package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class OperationRole extends Role {

    public static final String NAME = "OPERATION";
    public static final String CAPTION = "عملیات";

    public OperationRole() {
        super(NAME, CAPTION, new AdministrationRole());
    }

}
