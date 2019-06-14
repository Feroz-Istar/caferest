package rest.login;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import utils.CafeeDBUtils;

@Path("/profile")

public class RestProfile {
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@POST
	@Path("/all")
	public Response uploadProfile(String body) throws ParseException {

		JsonParser parser = new JsonParser();

		JsonObject json = parser.parse(body).getAsJsonObject();
		System.out.println("I am here" + json.get("email"));
		String email = json.get("email").getAsString();
		String password = json.get("password").getAsString();
		String name = json.get("name").getAsString();

		String dob = json.get("dob").getAsString();
		Date dob1 = new SimpleDateFormat("yyyy-MM-dd").parse(dob);

		JsonArray selectedItems = json.get("selectedItems").getAsJsonArray();
		String role_id = "";
		/*
		 * for (JsonElement jsonElement : selectedItems) {
		 * role_id+=jsonElement.getAsJsonObject().get("id")+","; }
		 */
		List<Integer> roles = new ArrayList<>();
		for (JsonElement selectedItem : selectedItems) {
			roles.add(selectedItem.getAsJsonObject().get("id").getAsInt());
		}

		// find the user whether its exist in the database or not if exits no need to
		// insert the user

		String sql = "INSERT INTO public.cafeuser ( email, password, created_at, updated_at) VALUES( '" + email + "', '" + password
							+ "', now(), NULL) ON conflict (email)  DO update set password ='" + password + "' returning id";
		// CafeeDBUtils.getInstance().insertIntoDB("");
		int id = CafeeDBUtils.getInstance().insertIntoDBWithGeneratedKey(sql);

		String profile_sql = "insert into user_profile(name, dob,user_id) values ('" + name + "','" + new java.sql.Timestamp(dob1.getTime()) + "'," + id
							+ ") on conflict(user_id) do nothing";
		CafeeDBUtils.getInstance().insertIntoDB(profile_sql);

		for (Integer integer : roles) {
			String role_sql = "insert into user_role (userid, role_id) values (" + id + "," + integer + ") on conflict do nothing";
			CafeeDBUtils.getInstance().insertIntoDB(role_sql);

		}
		String query = "select * from cafeuser where id=" + id;
		ArrayList<HashMap<String, String>> results = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), query);

		return Response.status(200).entity(gson.toJson(results)).header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}

	@POST
	@Path("/upload")
	public Response loadProfile(String body) throws ParseException {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(body).getAsJsonObject();
		String email = json.get("email").getAsString();
		String password = json.get("password").getAsString();
		String name = json.get("name").getAsString();
		String sql = "INSERT INTO public.cafeuser ( email, password, created_at, updated_at) VALUES( '" + email + "', '" + password
							+ "', now(), NULL) ON conflict (email)  DO update set password ='" + password + "' returning id";
		int user_id = CafeeDBUtils.getInstance().insertIntoDBWithGeneratedKey(sql);
		String dob = json.get("dob").getAsString();
		Date dob1 = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
		String profile_url = json.get("profile_url").getAsString();;
		int age = calculateAge(dob1);
		String profile_sql = "insert into user_profile(name, dob,user_id,age,profile_image) values ('" + name + "','" + new java.sql.Timestamp(dob1.getTime()) + "',"
							+ user_id + "," + age + ",'"+profile_url+"') on conflict(user_id) do nothing";
		CafeeDBUtils.getInstance().insertIntoDB(profile_sql);
		JsonArray selectedItems = json.get("selectedItems").getAsJsonArray();
		String role_id = "";
		/*
		 * for (JsonElement jsonElement : selectedItems) {
		 * role_id+=jsonElement.getAsJsonObject().get("id")+","; }
		 */
		List<Integer> roles = new ArrayList<>();
		for (JsonElement selectedItem : selectedItems) {
			roles.add(selectedItem.getAsJsonObject().get("id").getAsInt());
		}
		
		for (Integer integer : roles) {
			String role_sql = "insert into user_role (userid, role_id) values (" + user_id + "," + integer + ") on conflict do nothing";
			CafeeDBUtils.getInstance().insertIntoDB(role_sql);

		}
		String query = "select * from cafeuser where id=" + user_id;
		ArrayList<HashMap<String, String>> results = CafeeDBUtils.getInstance().executeQuery(Thread.currentThread().getStackTrace(), query);

		return Response.status(200).entity(gson.toJson(results)).header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
	}

	public static int calculateAge(Date birthdate) {
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthdate);
		Calendar today = Calendar.getInstance();

		int yearDifference = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

		if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
			yearDifference--;
		} else {
			if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
				yearDifference--;
			}

		}

		return yearDifference;
	}
}
