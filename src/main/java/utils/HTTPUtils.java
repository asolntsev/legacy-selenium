package utils;

import entities.Assets;
import helpers.Environment;
import helpers.StringManager;
import helpers.Verifications;
import logging.Log;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HTTPUtils {
    
    public static final String DEFAULT_ENCODING = "UTF-8";
    private static final String CREATE_ASSET_PATTERN = "id=\"Message\">Created asset";
    
    private enum RequestTypes {
        GET, POST
    }
    
    public static HttpURLConnection executePostRequest(String urlValue, Map<String, String> bodyParameters) throws
            IOException {
        return executePostRequest(urlValue, bodyParameters, null);
    }
    
    public static HttpURLConnection executePostRequest(String urlValue, Map<String, String> bodyParameters,
                                                       Map<String, String> headers) throws IOException {
        return executeRequest(RequestTypes.POST, urlValue, bodyParameters, headers);
    }
    
    public static HttpURLConnection executeGetRequest(String urlValue, Map<String, String> headers) throws IOException {
        return executeRequest(RequestTypes.GET, urlValue, null, headers);
    }
    
    public static HttpURLConnection executeGetRequest(String urlValue) throws IOException {
        return executeRequest(RequestTypes.GET, urlValue, null, null);
    }
    
    private static HttpURLConnection executeRequest(RequestTypes type, String urlValue, Map<String, String>
            bodyParameters, Map<String, String> headers) throws IOException {
        
        HttpURLConnection conn = setUrlConnectionAndAddHeaders(urlValue, headers);
        
        conn.setRequestMethod(type.toString());
        if (type == RequestTypes.POST && bodyParameters != null) {
            conn.setDoOutput(true);
            conn.getOutputStream().write(formParameters(bodyParameters));
        }
        return conn;
    }
    
    private static HttpURLConnection setUrlConnectionAndAddHeaders(String urlValue, Map<String, String> headers) throws IOException {
        URL url = new URL(urlValue);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        return conn;
    }
    
    private static byte[] formParameters(Map<String, String> params) throws IOException {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), DEFAULT_ENCODING));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), DEFAULT_ENCODING));
        }
        return postData.toString().getBytes(DEFAULT_ENCODING);
    }
    
    protected static String getResponseBody(HttpURLConnection connection) {
        StringBuilder response = new StringBuilder();
        String inputLine;
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            Log.logFailWithoutScreenshot("Failed to get body from response \n " + e.toString());
            e.printStackTrace();
        }
        return response.toString();
    }
    
    public static String uploadFile(Assets asset, Map<String, String> headers) throws IOException {
        String fileName = StringManager.getRandomAlphabetic() + asset.getExtension();
        
        FileBody file = new FileBody(new File(asset.getPath()), fileName, asset.getContentType(), DEFAULT_ENCODING.toLowerCase());
        
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
        multipartEntity.addPart("file", file);
        
        HttpURLConnection urlConnection = setUrlConnectionAndAddHeaders(Environment.getFileUploadUrl(), headers);
        
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(RequestTypes.POST.toString());
        urlConnection.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());
        
        OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
        try {
            multipartEntity.writeTo(outputStreamToRequestBody);
        } finally {
            outputStreamToRequestBody.close();
        }
        
        String response = getResponseBody(urlConnection);
        Verifications.assertContains(response, CREATE_ASSET_PATTERN, "Failed to create asset");
        
        return fileName;
        
    }
}
