package rest.login;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import utils.CafeeDBUtils;

@Path("/product")

public class ProductAssign {
	@POST
	@Produces("text/plain")
	@Path("/assign")
	public Response register(@QueryParam("product_id") String product_id,@QueryParam("category_id") String category_id) {
		System.out.println("product_id - "+product_id);
		System.out.println("category_id"+category_id);
		String sql = "update product set category_id ="+category_id+" where id ="+product_id;
		CafeeDBUtils.getInstance().insertIntoDB(sql);
		
		
		 return Response.ok("Sucess") //200
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
		
	}
}
