package ir.haji.nutro.exception;

public class DebugErrorResponse {
    String message;
    String exception;
    String[] stackTrace;

    public String effectiveLine(String packageName) {
        for (String s : stackTrace) {
            if (s.startsWith(packageName))
                return s;
        }
        return null;
    }

    public DebugErrorResponse(Exception e) {
        message = e.getMessage();
        exception = e.getClass().getCanonicalName();
        stackTrace = new String[e.getStackTrace().length];
        int i = 0;
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stackTrace[i] = stackTraceElement.toString();
            i++;
        }
    }

    public DebugErrorResponse(String message) {
        this.message = message;
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

    public String[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String[] stackTrace) {
        this.stackTrace = stackTrace;
    }
}
