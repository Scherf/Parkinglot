package de.signaliduna.parkinglot.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author u094915
 */
public class Person extends Entity {

  Person() {
    //
  }

  @NotNull
  @Size(min = 1, max = 30)
  private String name;

  @Size(min = 1, max = 30)
  private String surname;

  @NotNull
  @Valid
  private Rental rental;

  public Rental getRental() {
    return rental;
  }

  public void setRental(Rental rental) {
    this.rental = rental;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public static PersonBuilder builder() {
    return new PersonBuilder();
  }

  public static final class PersonBuilder {
    private String name;
    private String surname;

    private PersonBuilder() {
    }

    public PersonBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public PersonBuilder withSurname(String surname) {
      this.surname = surname;
      return this;
    }

    public Person build() {
      Person person = new Person();
      person.name = this.name;
      person.surname = this.surname;
      return person;
    }
  }
}
