package br.com.vfsilvacore.exception.handler;


import br.com.vfsilvacore.exception.VfsilvaException;
import br.com.vfsilvacore.exception.VfsilvaNotFoundException;
import br.com.vfsilvacore.exception.ValidationFieldException;
import br.com.vfsilvacore.util.message.DTOError;
import br.com.vfsilvacore.util.message.ErrorMessage;
import br.com.vfsilvacore.util.message.stack.ErrorStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Classe default Handler Exception das aplicações web.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class VfsilvaExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorStack errorStack;

    @ExceptionHandler(VfsilvaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(final VfsilvaNotFoundException ex, final HttpServletRequest request, final HttpServletResponse response) {

        log.error(ex.getErrorMessage().toString());
        return new ResponseEntity<>(new DTOError(ex.getErrorMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VfsilvaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(final VfsilvaException ex, final HttpServletRequest request) {

        log.error(ex.getErrorMessage().toString());
        return new ResponseEntity<>(new DTOError(ex.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(final ValidationFieldException ex, final HttpServletRequest request) {

        log.error(ex.getErrorMessage().toString());
        return new ResponseEntity<>(new DTOError(ex.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGenericException(final Exception ex, final HttpServletRequest request, final HttpServletResponse response) {

        errorStack.addMessage(ex.getMessage());

        final ErrorMessage error = ErrorMessage
                .builder()
                .title("Atenção")
                .error("Falha na aplicação")
                .details(errorStack.getErrors())
                .build();

        log.error(error.toString());

        return new ResponseEntity<>(new DTOError(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleRuntimeException(final RuntimeException ex, final HttpServletRequest request, final HttpServletResponse response) {

        errorStack.addMessage(ex.getMessage());

        final ErrorMessage error = ErrorMessage
                .builder()
                .title("Atenção")
                .error("Falha na aplicação")
                .details(errorStack.getErrors())
                .build();

        log.error(error.toString());

        return new ResponseEntity<>(new DTOError(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
