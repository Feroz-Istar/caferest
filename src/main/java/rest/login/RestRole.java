package rest.login;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import utils.CafeeDBUtils;

@Path("/role")
public class RestRole {
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@GET
	@Path("/all")
	public Response getAllRole() {
		
		
		Object output = null;
		String sql = "select * from cafeerole";
		ArrayList<HashMap<String, String>> k = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), sql);
		JsonArray jsonArray = new JsonArray();
		for (HashMap<String, String> hashMap : k) {
			JsonObject json = new JsonObject();
			json.addProperty("id",hashMap.get("id") );
			json.addProperty("itemName",hashMap.get("name") );
			jsonArray.add(json);

		}
		
		return Response.status(200).entity(jsonArray.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();

	}

}
