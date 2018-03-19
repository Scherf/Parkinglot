package de.signaliduna.parkinglot.model;

import java.util.List;

/**
 * Jeder {@link CarPark} besteht aus mehreren {@link Floor}s, welche {@link ParkingLot}s enthalten.
 * @author U094915
 */
public class Floor extends Entity {

  private int level;
  private int capacity;
  private List<ParkingLot> parkingLots;

  public int getLevel() {
    return level;
  }

  public int getCapacity() {
    return capacity;
  }

  public List<ParkingLot> getParkingLots() {
    return parkingLots;
  }

  public List<ParkingLot> addParkingLot(ParkingLot parkingLot) {
    parkingLots.add(parkingLot);
    return parkingLots;
  }

  @Override
  public String toString() {
    return "Floor{" +
            "level=" + level +
            ", capacity=" + capacity +
            ", parkingLots=" + parkingLots +
            '}';
  }

  public static FloorBuilder builder() {
    return new FloorBuilder();
  }

  public static final class FloorBuilder {
    private int level;
    private int capacity;
    private List<ParkingLot> parkingLots;

    private FloorBuilder() {
    }

    public FloorBuilder withLevel(int level) {
      this.level = level;
      return this;
    }

    public FloorBuilder withCapacity(int capacity) {
      this.capacity = capacity;
      return this;
    }


    public FloorBuilder withParkingLots(List<ParkingLot> parkingLots) {
      this.parkingLots = parkingLots;
      return this;
    }

    public Floor build() {
      Floor floor = new Floor();
      floor.level = this.level;
      floor.capacity = this.capacity;
      floor.parkingLots = this.parkingLots;
      return floor;
    }
  }
}
