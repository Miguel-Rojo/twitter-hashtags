package twitter.hashtags.exception;

public class ValidationException extends RuntimeException {

        private String message;

        public ValidationException(String message){
            super();
            this.message = message;
        }

    @Override
    public String getMessage() {
        return message;
    }
}
