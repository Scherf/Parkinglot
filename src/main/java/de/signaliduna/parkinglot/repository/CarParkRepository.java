package de.signaliduna.parkinglot.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import de.signaliduna.parkinglot.model.exception.ErrorCode;

/**
 * @author U094915
 */
@ApplicationScoped
public class CarParkRepository implements Repository<CarPark> {

  private Map<String, CarPark> carParks = new HashMap<>();

  public CarPark find(String name) {
    Optional<CarPark> carPark = carParks
            .entrySet()
            .stream()
            .filter(map -> map.getValue().getName().equalsIgnoreCase(name))
            .map(Map.Entry::getValue)
            .findFirst();
    return carPark.orElse(null);
  }

  public CarPark persist(CarPark carPark) {
    Objects.requireNonNull(carPark.getName(), "Carpark without name cannot be persisted");
    if (find(carPark.getName()) != null) {
      throw new BusinessException(ErrorCode.F1001);
    }
    carParks.put(carPark.getName(), carPark);
    return carPark;
  }
}
