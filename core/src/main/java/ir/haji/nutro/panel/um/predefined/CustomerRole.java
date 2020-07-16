package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Saeed on 8/14/17.
 */
@Component
public class CustomerRole extends Role {

    public static final String NAME = "CUSTOMER";
    public static final String CAPTION = "مشتریان";

    public CustomerRole() {
        super(NAME, CAPTION, new BasicRole());

    }

}
