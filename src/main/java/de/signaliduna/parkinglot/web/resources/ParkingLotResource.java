package de.signaliduna.parkinglot.web.resources;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.repository.ParkingLotRepository;

/**
 * Resource für den Web-Zugriff auf die Informationen des Parkplatzes.
 *
 * http://localhost:8080/rest/parkinglot/ping
 *
 */
@RequestScoped
@Path("/parkinglot")
//@Api(value = "/parkinglot", description = "Schnittstelle zur Abfrage der Parkplätze.")
public class ParkingLotResource {

  @Inject
  private ParkingLotRepository parkingLotRepository;

  public ParkingLotResource() {
    //
  }

  @GET
  @Produces("text/plain")
  @Path("ping")
  public Response ping() {
    return Response
            .ok(LocalDateTime.now())
            .build();
  }

  /**
   * Liefert eine Liste aller bekannten {@link ParkingLot}s.
   */
  @GET
  @Path("all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<ParkingLot> getParkingLots() {
    return parkingLotRepository.findAllParkingLots();
  }
}
