package de.signaliduna.parkinglot.parkinglot.boundary;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Registrierungsklasse für die Resourcen der Anwendung.
 */
@ApplicationPath("/rest")
public class ParkingLotApplication extends Application {

  public ParkingLotApplication() {
    //
  }
}
