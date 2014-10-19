package org.landcycle.controller.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.landcycle.api.LikeItem;
import org.landcycle.api.Position;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;

public class TestController {

	@Test
	public void testMime(){
		String tg = "data:image/jpeg;base64,11111";
		int end = tg.indexOf(";");
		int start = tg.indexOf(":");
		
		String mime = tg.substring(start+1,end);
		System.out.println(mime);
	}
	


	@Test
	public void testLike() {
		try {
			LikeItem like = new LikeItem();
			like.setId("dddd-8fd1-4ef3-9fe8-fdaac4315fd9");
			like.setUser("massimiliano.regis@gmail.com");
			HttpPost postRequest = new HttpPost("http://localhost:8080/relay-service-web/rest/land/Like");
			postRequest.addHeader("Content-Type", "application/json");
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(like);
			System.out.println(jjson);
			StringEntity input = new StringEntity(jjson);
			postRequest.setEntity(input);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(postRequest);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testComment() {
		try {
			LikeItem like = new LikeItem();
			like.setId("dddd-8fd1-4ef3-9fe8-fdaac4315fd9");
			like.setUser("massimiliano.regis@gmail.com");
			HttpPost postRequest = new HttpPost("http://localhost:8080/relay-service-web/rest/land/Like");
			postRequest.addHeader("Content-Type", "application/json");
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(like);
			System.out.println(jjson);
			StringEntity input = new StringEntity(jjson);
			postRequest.setEntity(input);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(postRequest);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		try {
			
			String b64file = encodeFileToBase64Binary("/Users/valerio/XDOCUMENTI/IMMAGINI/__MOTO/696/03.jpg");
			String mime = "data:image/jpg;base64,";
			UUID uuidFile = UUID.randomUUID();
			UserItem user = new UserItem();
			User u = new User();
			u.setMail("valerio.artusi@gmail.com");
			user.setUser(u);
			TaggableItem forSale = new TaggableItem();
			forSale.setStream(mime+b64file);
			forSale.setDescription("test");
			forSale.setName("test");
			forSale.setId(uuidFile.toString());
			forSale.setImageType("jpg");
			Position pos = new Position();
			pos.setLat(new Double("45.5705544").doubleValue());
			pos.setLng(new Double("8.0548405").doubleValue());
			forSale.setPosition(pos);
			user.setTaggable(forSale);
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(user);
			System.out.println(jjson);
			StringEntity input = new StringEntity(jjson);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/relay-service-web/rest/land");
//			 HttpPost postRequest = new
//			 HttpPost("http://95.110.224.34:8080/relay-service-web/rest/land");

			postRequest.addHeader("Content-Type", "application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			// if (response.getStatusLine().getStatusCode() != 201) {
			// throw new RuntimeException("Failed : HTTP error code : "
			// + response.getStatusLine().getStatusCode());
			// }

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			// httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void update() {
		try {
			UserItem user = new UserItem();
			User u = new User();
			u.setMail("massimiliano.regis@gmail.com");
			user.setUser(u);
			TaggableItem taggable = new TaggableItem();
			taggable.setDescription("test");
			taggable.setId("c03245bf-1265-486e-9472-984579f93960");
			
			Position pos = new Position();
			// pos.setLat(new Double("45.3194077").doubleValue());
			// pos.setLng(new Double("9.5237682").doubleValue());
			pos.setLat(new Double("45.57055440").doubleValue());
			pos.setLng(new Double("8.05484050").doubleValue());
			taggable.setPosition(pos);
//			forSale.setStream(null);
			user.setTaggable(taggable);
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(user);
			System.out.println(jjson);
			StringEntity input = new StringEntity(jjson);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPut postRequest = new HttpPut("http://localhost:8080/relay-service-web/rest/land");
			// input.setContentType("text/xml; charset=ISO-8859-1");
			input.setContentType("application/json");
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.setEntity(input);
			// httpCon.setRequestMethod("PUT");
			HttpResponse response = httpClient.execute(postRequest);
			// if (response.getStatusLine().getStatusCode() != 201) {
			// throw new RuntimeException("Failed : HTTP error code : "
			// + response.getStatusLine().getStatusCode());
			// }

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			// httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findAround() {

	}

	private String encodeFileToBase64Binary(String fileName) throws IOException {

		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encodeBase64(bytes);
		String encodedString = new String(encoded);

		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		is.close();
		return bytes;
	}
}
