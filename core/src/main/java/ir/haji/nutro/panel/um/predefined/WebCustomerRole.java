package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Saeed on 8/14/17.
 */
@Component
public class WebCustomerRole extends Role {

    public static final String NAME = "WEB_CUSTOMER";
    public static final String CAPTION = "مشتریان وب";

    public WebCustomerRole() {
        super(NAME, CAPTION, new BasicRole());

    }

}
