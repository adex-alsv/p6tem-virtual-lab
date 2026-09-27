public class InvalidParameterException extends Exception {
    private final String parameterName;

    public InvalidParameterException(String parameterName, String message) {
        super(message);
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }
}