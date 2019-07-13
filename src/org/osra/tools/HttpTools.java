package org.osra.tools;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;



public class HttpTools {

	public static RestResponse doJSONPost(URL url, String body, String user, String password) throws IOException{
		String encoding;
		String line;
		RestResponse response= new RestResponse();
		HttpURLConnection connection = null;
		OutputStreamWriter osw = null;
		BufferedReader in = null;
		BufferedReader inError = null;


		try {
			encoding = Base64.getEncoder().encodeToString((user + ":"+ password).getBytes("UTF-8"));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			OutputStream os = connection.getOutputStream();
			osw = new OutputStreamWriter(os, "UTF-8");    
			osw.write(body);
			osw.flush();

			//MAYBE COMENTAR
			InputStream content = (InputStream)connection.getInputStream();
            in = new BufferedReader (new InputStreamReader (content));
            String str = "";
            while ((line = in.readLine()) != null) {
            	str += line+"\n";
            }

            response.setMessage(str);
			response.setCode(connection.getResponseCode());
			
		} catch (IOException e) {
			throw new IOException(e);
		}
		finally{
			if(osw != null)
				osw.close();
			if(connection != null)
				connection.disconnect();
			if(in != null)
				in.close();
			if(inError != null)
				inError.close();
		}
		return response;
	}

	public static RestResponse doDelete(URL url, String user, String password) throws IOException{
		String encoding;
		String line;
		RestResponse response= new RestResponse();
		HttpURLConnection connection = null;
		OutputStreamWriter osw = null;
		BufferedReader in = null;
		BufferedReader inError = null;
		
		try {
			encoding = Base64.getEncoder().encodeToString((user + ":"+ password).getBytes("UTF-8"));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			
			//MAYBE COMENTAR
			InputStream content = (InputStream)connection.getInputStream();
            in = new BufferedReader (new InputStreamReader (content));
            String str = "";
            while ((line = in.readLine()) != null) {
            	str += str+"\n";
            }
			
			response.setMessage(str);
			response.setCode(connection.getResponseCode());


		} catch (IOException e) {
			throw new IOException(e);
		}
		finally{
			if(osw != null)
				osw.close();
			if(connection != null)
				connection.disconnect();
			if(in != null)
				in.close();
			if(inError != null)
				inError.close();
		}
		return response;
	}

	public static RestResponse doJSONGet(URL url, String user, String password) throws IOException{
		String encoding;
		RestResponse response = new RestResponse();
		String line;
		String json="";
		HttpURLConnection connection = null;
		
		try {
			encoding = Base64.getEncoder().encodeToString((user + ":"+ password).getBytes("UTF-8"));
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			
			InputStream content = (InputStream)connection.getInputStream();
			response.setCode(connection.getResponseCode());
			BufferedReader in   = 
					new BufferedReader (new InputStreamReader (content));
			while ((line = in.readLine()) != null) {
				//System.out.println(line);
				json += line+"\n";
			}
			response.setMessage(json);
		} catch (IOException e) {
			throw new IOException(e);
		}
		finally{
			if(connection != null)
				connection.disconnect();
		}
		
		return response;
	}

}
