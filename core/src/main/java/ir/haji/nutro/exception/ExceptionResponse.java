package ir.haji.nutro.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExceptionResponse implements Serializable {
    String message;
    String exception;
    List<String> stackTrace;

    public ExceptionResponse(Exception ex, Boolean debug) {
        ex.printStackTrace();
        message = ex.getMessage();
        if (!debug) {
            exception = ex.getClass().getCanonicalName();
            stackTrace = new ArrayList<>();
            for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
                stackTrace.add(stackTraceElement.toString());
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
}
