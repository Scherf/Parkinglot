package de.signaliduna.parkinglot.model.exception;

/**
 * Indicated business error.
 *
 * @author U094915
 */
public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
