package ir.haji.nutro.exception;

import ir.haji.nutro.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @Value("${panel.debug:false}")
    Boolean debug;

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            if (value == null || value.isEmpty() || !CommonUtil.isNumeric(value)) {
                                setValue(null);
                                return;
                            }
                            Long timestamp = CommonUtil.castToLong(value);
                            if (timestamp < 0) {
                                setValue(null);
                                return;
                            }
                            Date parsedDate = new Date(timestamp);
                            setValue(parsedDate);
                        } catch (ParseException e) {
                            setValue(null);
                        }
                    }
                }
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public
    @ResponseBody
    ExceptionResponse badRequest(Exception ex) {
        return new ExceptionResponse(ex, this.debug);
    }


}
