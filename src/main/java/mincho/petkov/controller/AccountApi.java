package mincho.petkov.controller;

import mincho.petkov.model.dto.AccountDetail;
import mincho.petkov.model.dto.TransferRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
public interface AccountApi {


    /**
     * get all account
     *
     * @return List of Account Details
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<AccountDetail> getAll();

    /**
     * get account by ID
     *
     * @param id account ID
     * @return Response
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getById(@PathParam("id") String id);

    /**
     * Make a transfer
     *
     * @param transferRequest a transfer request
     * @return Response
     */
    @Path("/transfer")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response makeTransfer(TransferRequest transferRequest);
}
