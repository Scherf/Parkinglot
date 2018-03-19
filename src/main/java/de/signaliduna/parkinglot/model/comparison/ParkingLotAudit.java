package de.signaliduna.parkinglot.model.comparison;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import de.signaliduna.parkinglot.model.ParkingLot;

/**
 * @author U094915
 */
public class ParkingLotAudit {

  public Diff compare(ParkingLot origin, ParkingLot target) {
    Javers comparator = JaversBuilder.javers().build();
    return comparator.compare(origin, target);
  }
}
