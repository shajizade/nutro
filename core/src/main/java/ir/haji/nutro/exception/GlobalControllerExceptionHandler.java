package ir.haji.nutro.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @Value("${panel.debug:false}")
    Boolean debug;
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DebugErrorResponse> exception(Exception e) {
        DebugErrorResponse response = new DebugErrorResponse(e);
        System.out.println(e.getClass().getCanonicalName() + " | " + response.effectiveLine("ir.haji"));
        if (!debug)
            response = new DebugErrorResponse(e.getMessage() == null ? "خطایی رخ داده است" : e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
