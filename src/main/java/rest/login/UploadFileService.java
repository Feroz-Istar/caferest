package rest.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.api.client.json.Json;
import com.google.gson.JsonObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/file")
public class UploadFileService {

	private static final String FILE_FOLDER = "/tmp/feroz/";

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = FILE_FOLDER + fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation;
		System.out.println(output);
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("success", true);
		jsonObject.addProperty("status", "success");
		jsonObject.addProperty("filename", fileDetail.getFileName());
		return Response.status(200).entity(jsonObject.toString()).header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").allow("OPTIONS").build();
		

	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@OPTIONS
	@Path("/upload")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile() {
		return Response.status(200).entity("success").header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
							.header("Access-Control-Allow-Headers", "accept, Cache-Control, content-type, x-requested-with").allow("OPTIONS")
							.build();
	}

	@GET
	@Path("/download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyQuery(@QueryParam("filename") String fileName) {
		return download(fileName);
	}

	@GET
	@Path("/download/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFilebyPath(@PathParam("filename") String fileName) {
		return download(fileName);
	}

	private Response download(String fileName) {
		String fileLocation = FILE_FOLDER + fileName;
		Response response = null;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);

		// Retrieve the file
		File file = new File(FILE_FOLDER + fileName);
		if (file.exists()) {
			ResponseBuilder builder = Response.ok(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			response = builder.build();

			long file_size = file.length();

		} else {

			response = Response.status(404).entity("FILE NOT FOUND: " + fileLocation).type("text/plain").build();
		}

		return response;
	}
}
