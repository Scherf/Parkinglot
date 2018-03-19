package de.signaliduna.parkinglot.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author u094915
 */
public abstract class Entity implements Serializable {

  Entity() {
    this.id = UUID.randomUUID();
  }

  @NotNull
  @Id
  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Entity{" +
            "id=" + id +
            '}';
  }
}
