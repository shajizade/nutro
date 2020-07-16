package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Saeed on 8/14/17.
 */
@Component
public class UnauthorizedRole extends Role {

    public static final String NAME = "UNATHORIZED_ROLE";
    public static final String CAPTION = "نقش برای مجوز ورود بدون login ";

    public UnauthorizedRole() {
        super(NAME, CAPTION, null);

    }

}
