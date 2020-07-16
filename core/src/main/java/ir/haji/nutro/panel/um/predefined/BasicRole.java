package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by Saeed on 8/14/17.
 */
@Component
public class BasicRole extends Role {

    public static final String NAME = "BASIC_ROLE";
    public static final String CAPTION = "نقش پایه";

    public BasicRole() {
        super(NAME, CAPTION, null);

    }

}
