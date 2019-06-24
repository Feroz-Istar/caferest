package rest.login;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import apperror.AuthErrorMessage;
import utils.CafeeDBUtils;

@Path("/login")
public class LoginAuth {

	@GET
	public String getMsg() {
		return "Hello World !! - Jersey 2";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("profile")
	public Response fetchProfile(@QueryParam("user_id") String user_id) {
		String sql = "Select * from user_profile where user_id=" + user_id;
		ArrayList<HashMap<String, String>> resultset = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), sql);
		if (resultset != null && resultset.size() > 0) {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.enableComplexMapKeySerialization().create();
			Type type = new TypeToken<HashMap<String, String>>() {

			}.getType();
			return Response.ok(gson.toJson(resultset.get(0))) // 200
								.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.allow("OPTIONS").build();
		} else {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("error", AuthErrorMessage.AUTH_FAILURE);
			return Response.status(500).entity(jsonObject.toString()) // 200
								.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.allow("OPTIONS").build();
		}
	}

	@POST
	@Produces("text/plain")
	@Path("/register")
	public Response register(@FormParam("name") String name, @FormParam("email") String email, @FormParam("mobile") String mobile,
						@FormParam("password") String password) {
		System.out.println("name - " + name);
		System.out.println("email - " + email);
		System.out.println("mobile - " + mobile);
		String sql = "INSERT INTO public.cafeuser(name, email, mobile,password,create_at) VALUES('" + name + "', '" + email + "', '" + mobile + "','"
							+ password + "',now())";
		CafeeDBUtils.getInstance().insertIntoDB(sql);

		return Response.ok("Sucess") // 200
							.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
							.allow("OPTIONS").build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Response auth(@FormParam("email") String email, @FormParam("password") String password) {
		System.out.println("email - " + email);
		String sql = "select * from cafeuser where (lower(email)='" + email.toLowerCase() + "') and password = '" + password + "'";
		System.out.println(sql);
		ArrayList<HashMap<String, String>> resultset = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), sql);

		if (resultset != null && resultset.size() > 0) {
			for (HashMap<String, String> map : resultset) {
				System.out.println(map.get("id") + " ...........................................");
			}
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.enableComplexMapKeySerialization().create();
			Type type = new TypeToken<HashMap<String, String>>() {

			}.getType();
			return Response.ok(gson.toJson(resultset.get(0))) // 200
								.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.allow("OPTIONS").build();

		} else {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("error", AuthErrorMessage.AUTH_FAILURE);
			return Response.status(500).entity(jsonObject.toString()) // 200
								.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
								.allow("OPTIONS").build();
		}

	}

}
