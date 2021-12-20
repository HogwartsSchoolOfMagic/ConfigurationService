package io.github.hogwartsschoolofmagic.configuration.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception java class for catching and handling all errors thrown by controllers.
 *
 * @author Vladislav [SmithyVL] Kuznetsov.
 * @since 0.4.5
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ConfigurationControllerAdvice extends ResponseEntityExceptionHandler {

  /**
   * Exception handle method for handling all other server errors - Internal Server Error.
   *
   * @param ex method-related exception.
   * @return response data error from the server.
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleInternal(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), INTERNAL_SERVER_ERROR);
  }

  /**
   * Exception handle method for errors in reading query parameters.
   *
   * @param ex      type mismatch exception.
   * @param headers request headers.
   * @param status  request status.
   * @param request request data.
   * @return error response from server.
   */
  @NonNull
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), BAD_REQUEST);
  }

  /**
   * Exception handle method for parameter validation errors in REST methods.
   *
   * @param ex      type mismatch exception.
   * @param headers request headers.
   * @param status  request status.
   * @param request request data.
   * @return error response from server.
   */
  @NonNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                @NonNull HttpHeaders headers,
                                                                @NonNull HttpStatus status,
                                                                @NonNull WebRequest request) {
    List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    return new ResponseEntity<>(errors, new HttpHeaders(), BAD_REQUEST);
  }

  /**
   * Exception handle method for the data type does not match in the request and in the parameters
   * of the request method.
   *
   * @param ex      type mismatch exception.
   * @param headers request headers.
   * @param status  request status.
   * @param request request data.
   * @return error response from server.
   */
  @NonNull
  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                      @NonNull HttpHeaders headers,
                                                      @NonNull HttpStatus status,
                                                      @NonNull WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), BAD_REQUEST);
  }
}