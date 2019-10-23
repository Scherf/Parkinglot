package de.signaliduna.parkinglot.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author u094915
 */
@Entity
@Getter
@Setter
public class Person implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(min = 1, max = 30)
  private String name;

  @Size(min = 1, max = 30)
  private String surname;

  @Valid
  @OneToMany
  @JsonbTransient
  private List<Rental> rentalList = new ArrayList<>();

  public static PersonBuilder builder() {
    return new PersonBuilder();
  }

  void addRental(Rental rental) {
    Objects.requireNonNull(rental, "Rental must not be null");
    rentalList.add(rental);
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
