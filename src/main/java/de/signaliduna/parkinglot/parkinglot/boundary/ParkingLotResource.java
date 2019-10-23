package de.signaliduna.parkinglot.parkinglot.boundary;

import java.time.LocalDateTime;
import java.util.List;

import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.repository.ParkingLotRepository;
import javax.ws.rs.core.UriInfo;

/**
 * Resource für den Web-Zugriff auf die Informationen des Parkplatzes.
 * <p>
 * http://localhost:8080/rest/parkinglot/ping
 */
@RequestScoped
@Path("/parkinglot")
//@Api(value = "/parkinglot", description = "Schnittstelle zur Abfrage der Parkplätze.")
public class ParkingLotResource {

  @Inject
  ParkingLotRepository parkingLotRepository;

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

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response find(@PathParam("id") int id) {
    ParkingLot result = parkingLotRepository.findById(id);
    Response response;
    if (result != null) {
      response =
          Response
              .ok(result)
              .build();
    } else {
      response = Response
                     .serverError()
                     .header("error", "Parkinglot not found!")
                     .build();
    }
    return response;
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(ParkingLot parkingLot, @Context UriInfo uriInfo) {
    // set back reference.
    parkingLot.getRental().setParkingLot(parkingLot);
    @NotNull ParkingLot result = parkingLotRepository.persist(parkingLot);
    Response response;
    if (result != null) {
      response =
          Response
              .created(uriInfo
                           .getAbsolutePathBuilder()
                           .path(String.valueOf(result.getId()))
                           .build())
              .build();
    } else {
      response = Response
                     .serverError()
                     .header("error", "parkingLot not created!")
                     .build();
    }
    return response;
  }
}
