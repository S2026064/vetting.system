/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.vetting.system.service;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sars.vetting.system.domain.JsonDocumentDto;

/**
 *
 * @author S2026987
 */
@Service
public class DocumentAttachmentService implements DocumentAttachmentServiceLocal {

//    @Autowired
//    private DocumentationServiceLocal documentationServiceLocal;
    private RestTemplate restTemplate;

    private final String UPLOAD_DOCUMENT_URL = "http://10.30.5.217:9082/sars_pca/rest/sarsdocument/upload";
    private final String DOWNLOAD_DOCUMENT_URL = "http://10.30.5.217:9082/sars_pca/rest/sarsdocument/properties/{0}";

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    public DocumentAttachmentService() {
         restTemplate = new RestTemplate(getClientHttpRequestFactory());
//        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
//        restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(500)).setReadTimeout(Duration.ofSeconds(500)).build();
    }

    @Override
    public JsonDocumentDto getDocumentJsonValue(String code) {
        JsonDocumentDto response = restTemplate.getForObject(DOWNLOAD_DOCUMENT_URL, JsonDocumentDto.class, code);
        return response;
    }

    @Override
    public String uploadDocument(JsonDocumentDto document) {

        String result = "";
        JSONObject myResponse = null;

        Gson gSon = new Gson();
        String jsonString = gSon.toJson(document);

        //Dev URL - Don't try now
        // String query_url_post ="http://10.30.5.217:9080/sars_pca/rest/sarsdocument/upload"; 
        //PreProd URL
        String query_url_post = "http://10.30.6.41:9082/pca/rest/sarsdocument/upload";
        try {
            URL url = new URL(query_url_post);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(jsonString.getBytes("UTF-8"));
            System.out.println(os);
            os.close();

            // read the response 
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            System.out.println("JSON Response...........");
            myResponse = new JSONObject(result);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getLocalizedMessage());
        }

        return result;
    }

    @Override
    public void download(String objectId) {

        //Dev URL - Don't try Dev Environment
        //String query_url_post ="http://10.30.5.217:9080/rest/sarsdocument/download/09000384801f11da"; 
        //PreProd URL
        String query_url_get = "http://10.30.6.41:9082/pca/rest/sarsdocument/download/" + objectId;
        try {

            URL url = new URL(query_url_get);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String result = "";
            JSONObject myResponse1 = null;
            System.out.println("Output from Server .... \n");

            while ((result = br.readLine()) != null) {
                myResponse1 = new JSONObject(result);

                String content = myResponse1.getString("content");
                System.out.println("Output JSON: " + content);
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter("./document.txt"))) {
//                    writer.flush();
                    writer.write(content);
                }

            }

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception : " + e.getLocalizedMessage());
        }

    }

}
