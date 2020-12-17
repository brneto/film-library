package ie.ait.agile.filmlibrary.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private CustomErrorMessage logAndRespond(Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return new CustomErrorMessage(ex.getLocalizedMessage(), ex.getClass().getTypeName());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    CustomErrorMessage handleIllegalArgument(IllegalStateException ex) {
        return logAndRespond(ex);
    }

    private static class CustomErrorMessage {
        private final String customMessage;
        private final String exception;

        CustomErrorMessage(String customMessage, String exception) {
            this.customMessage = customMessage;
            this.exception = exception;
        }

        public String getCustomMessage() {
            return customMessage;
        }

        public String getException() {
            return exception;
        }
    }

}
