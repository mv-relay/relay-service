package org.landcycle.controller.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.landcycle.api.LikeItem;
import org.landcycle.api.Position;
import org.landcycle.api.Taggable;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;

public class TestController {

	@Test
	public void testUpload() {
		try {
			// String b64file =
			// encodeFileToBase64Binary("/Users/valerio/Pictures/HM-1100evoSP_2011_Studio_R_D01_[1920x1280]_mediagallery_output_image_[1920x1080].png");
			// String b64file =
			// encodeFileToBase64Binary("/Users/valerio/Pictures/moto.png");
			String b64file = encodeFileToBase64Binary("/Users/valerio/XDOCUMENTI/IMMAGINI/2009_NEVE/DSC00595.JPG");
			// System.out.println(b64file);
			// HttpClient client = new DefaultHttpClient();
			// HttpPost post = new
			// HttpPost("http://localhost:7001/arch-fe-jbus-mediabus-web/rest/mb/media/upload");
			// HttpPost post = new
			// HttpPost("http://localhost:7001/mb/Avatar/Upload2");
			// HttpPost post = new
			// HttpPost("http://lxurv720.gbm.lan:8001/mb/media/upload");
			// HttpPost post = new
			// HttpPost("http://svil-mobile.bmednet.it:8080/mb/media/upload");
			// HttpPost post = new
			// HttpPost("https://test-mobile.bmednet.it/mb/media/upload");
			// post.setHeader("Cookie",
			// "JSESSIONID=xEZnOH4JY16oz0SkI8JoN0xNXjK6yEg0HOa6YLR6qJRdWFjPnQwD");

			//
			// CookieStore cookieStore = new BasicCookieStore();
			// BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
			// "GYepfdPDwWdmSXHrYLRCpLmZN5QmdQNRds41bhmKusoUqVgFeDlC");
			//
			// //cookie.setDomain("your domain");
			// cookie.setPath("/");
			//
			// cookieStore.addCookie(cookie);
			// ((AbstractHttpClient) client).setCookieStore(cookieStore);

			UserItem user = new UserItem();
			User u = new User();
			u.setMail("valerio.artusi");
			u.setFirstName("valerio");
			user.setUser(u);
			Taggable forSale = new Taggable();
			forSale.setDescription("test");
			forSale.setStream(b64file);

			Position pos = new Position();
			pos.setLat(new Double("45.3194077").doubleValue());
			pos.setLng(new Double("9.5237682").doubleValue());
			// user.setPosition(pos);
			user.setTaggable(forSale);
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(user);
			StringEntity input = new StringEntity(jjson);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/land-web/rest/land");

			// input.setContentType("application/json");
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sendFile() {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://localhost:8080/land-web/rest/land/Upload");
			String fileName = "/Users/valerio/XDOCUMENTI/IMMAGINI/__MOTO/696/03.jpg";
			FileBody bin = new FileBody(new File(fileName));
			StringBody comment = new StringBody("Filename: " + fileName);
			httppost.addHeader("Content-type", "multipart/form-data");
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", bin);
			reqEntity.addPart("file_name", new StringBody(""));
			reqEntity.addPart("folder_id", new StringBody(""));
			reqEntity.addPart("description", new StringBody(""));
			reqEntity.addPart("source", new StringBody(""));
			reqEntity.addPart("file_type", new StringBody("jpg"));
			// reqEntity.addPart("data", bin);

			// reqEntity.addPart("comment", comment);
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			System.out.println(resEntity);
		} catch (Exception e) {

		}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void save() {
		try {
			UserItem user = new UserItem();
			User u = new User();
			u.setMail("massimiliano.regis@gmail.com");
			user.setUser(u);
			Taggable forSale = new Taggable();
			forSale.setDescription("test");
			forSale.setId("ddddds-8fd1-4ef3-9fe8-fdaac4315fd9");
			forSale.setImageType("jpg");
			Position pos = new Position();
			// pos.setLat(new Double("45.3194077").doubleValue());
			// pos.setLng(new Double("9.5237682").doubleValue());
			pos.setLat(new Double("45.5705544").doubleValue());
			pos.setLng(new Double("8.0548405").doubleValue());
			forSale.setPosition(pos);
			forSale.setStream(null);
			user.setTaggable(forSale);
			ObjectMapper mapper = new ObjectMapper();
			String jjson = mapper.writeValueAsString(user);
			System.out.println(jjson);
			StringEntity input = new StringEntity(jjson);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://localhost:8080/relay-service-web/rest/land");

			// input.setContentType("application/json");
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
			Taggable forSale = new Taggable();
			forSale.setDescription("test");
			forSale.setId("fb0548ca-8fd1-4ef3-9fe8-fdaac4315fd9");
			forSale.setImageType("jpg");
			forSale.setMailAcq("valerioz.artusi@gmail.com");
			Position pos = new Position();
			// pos.setLat(new Double("45.3194077").doubleValue());
			// pos.setLng(new Double("9.5237682").doubleValue());
			pos.setLat(new Double("45.57055440").doubleValue());
			pos.setLng(new Double("8.05484050").doubleValue());
			forSale.setPosition(pos);
			forSale.setStream(null);
			user.setTaggable(forSale);
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
