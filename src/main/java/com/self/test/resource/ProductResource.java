package com.self.test.resource;

import com.self.test.core.DatabaseUtility;
import com.self.test.model.Id;
import com.self.test.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/products")
public class ProductResource {

    @Context
    protected UriInfo uriInfo;

    @Path("{code}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProduct(@PathParam("code") String code){
        System.out.println("code: " + code);
        final Product product;
        try {
            product = DatabaseUtility.retrieveProduct(code);
            if(product.getCode()== null)
                return Response.noContent().build();
            else
                return Response.ok(product).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NO_CONTENT).build();
        }

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addProduct(final Product product){
        System.out.println("Product name:" + product.getName());
        System.out.println("Product code:" + product.getCode());
        System.out.println("Product code:" + product.getPrice());

        try {
            DatabaseUtility.insertProduct(product.getCode(), product.getName(), product.getPrice());
            final URI orderResourceLocation = uriInfo.getAbsolutePathBuilder().path(product.getCode()).build();
            final Id id = new Id();
            id.setId(product.getCode());
            return Response.created(orderResourceLocation).entity(id).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


    }
}
