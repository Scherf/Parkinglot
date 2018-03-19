package de.signaliduna.parkinglot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Rental;

/**
 * Gemäß DDD Zugriff auf die {@link de.signaliduna.parkinglot.model.ParkingLot} Instanzen.
 */
@ApplicationScoped
public class ParkingLotRepository implements Repository<ParkingLot> {

  private static List<ParkingLot> parkingLots = new ArrayList<>();

  public ParkingLotRepository() {
    //
  }

  @NotNull
  public List<ParkingLot> findAllParkingLots() {

    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(666)
            .withRental(new Rental())
            .build();
    parkingLots.add(parkingLot);
    return parkingLots;
  }

  @NotNull
  public ParkingLot persist(ParkingLot parkingLot) {
    if (Objects.isNull(parkingLot.getId())
            || parkingLots.stream().noneMatch(p -> p.getId().equals(parkingLot.getId()))) {
      parkingLots.add(parkingLot);
    }
    return parkingLot;
  }

  public void clear() {
    parkingLots = new ArrayList<>();
  }
}
