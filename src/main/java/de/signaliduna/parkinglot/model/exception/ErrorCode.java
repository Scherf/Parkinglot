package de.signaliduna.parkinglot.model.exception;

/**
 * Container für die Fehlermeldungen mit Code und Klartext.

 * @author U094915
 */
public enum ErrorCode {

  F1000("Parkinglot already leased."),
  F1001("Parkinglot already exists.");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

  public String getMessage(){
    return this.message;
  }



}
