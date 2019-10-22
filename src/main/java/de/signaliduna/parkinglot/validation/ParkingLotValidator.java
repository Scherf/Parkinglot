package de.signaliduna.parkinglot.validation;

import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import de.signaliduna.parkinglot.model.ParkingLot;

/**
 * @author U094915
 */
@RequestScoped
public class ParkingLotValidator {

  @Inject
  private Validator validator;

  Set<ConstraintViolation<ParkingLot>>  validate(ParkingLot parkingLot) {
    return validator.validate(parkingLot);
  }

}
