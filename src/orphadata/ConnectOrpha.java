package orphaData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oracle.javafx.jmx.json.JSONReader;
import model.Disease;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by meh on 10/05/2017.
 */

public class ConnectOrpha {


    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();


    private String host     = "http://couchdb.telecomnancy.univ-lorraine.fr";
    private String db       = "orphadatabase";
    public ArrayList<Disease> Diseases = new ArrayList<Disease>();
    public HashMap<String, String> orphaSigns;


    public JSONObject get(String id) throws Exception {
        return new JSONObject(send("GET", this.getURL(id), null));
    }

    public URL getURL(String path) throws Exception {
        URL url = new URL(this.host + "/" + db + path);
        //System.out.println(url);
        return new URL(new URI(
                url.getProtocol(),
                url.getUserInfo(),
                url.getHost(),
                url.getPort(),
                url.getPath(),
                url.getQuery(),
                url.getRef()
        ).toASCIIString());
    }


    public ArrayList<Disease> getDiseaseByName(String disease) throws Exception {
        String url = "/_design/diseases/_view/GetDiseasesByName";
        JSONObject myJson = get(url + "?startkey=\"" + Character.toUpperCase(disease.charAt(0)) + disease.substring(1).toLowerCase() + "\"&endkey={}");
        System.out.println(url +"?key=\"" +Character.toUpperCase(disease.charAt(0)) + disease.substring(1).toLowerCase() +"\"");
        JSONArray rows = myJson.getJSONArray("rows");
        ArrayList<Disease> DiseaseesOrpha = new ArrayList<Disease>();

        System.out.println("rows: " + rows);

        if (rows.length() > 0) {
            for (int i = 0; i < rows.length(); i++) {
                Disease myDisease = new Disease();
                if (rows.getJSONObject(i).getString("key").contains(disease)) {

                    JSONObject global = rows.getJSONObject(i);
                    JSONObject allValues = global.getJSONObject("value");

                    //Disease name
                    myDisease.setNom(allValues.getJSONObject("Name").getString("text"));

                    //OrphaNumber
                    myDisease.setId(allValues.getInt("OrphaNumber"));


                    System.out.println("saluuut");
                    System.out.println(3);

                    JsonElement je = jp.parse(myJson.toString());
                    String prettyJsonString = gson.toJson(je);
                    // System.out.println(prettyJsonString);

                    //ID OMIM
                    try {
                         // à faire

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //List of Signs
                    // getSignByKey(allValues.getInt("OrphaNumber"), myDisease);
                    DiseaseesOrpha.add(myDisease);
                }
            }
        } else {
            System.out.println("No matching");
        }
        return DiseaseesOrpha;
    }


    public void getSignByKey(int orphaNum, Disease myDisease) throws Exception {
        String url = "/_design/clinicalsigns/_view/GetDiseaseClinicalSignsNoLang";
        JSONObject myJson = get(url + "?key=" + orphaNum);
        orphaSigns = new HashMap<String, String>();
        JSONArray rows = myJson.getJSONArray("rows");
        if (rows.length() > 0) {
            for (int i = 0; i < rows.length(); i++) {
                JSONObject global = rows.getJSONObject(i);
                String myList = global.getJSONObject("value").getJSONObject("clinicalSign").getJSONObject("Name").getString("text");
                String frequency = global.getJSONObject("value").getJSONObject("data").getJSONObject("signFreq").getJSONObject("Name").getString("text");
                if (orphaSigns.containsKey(frequency)) {
                    myList += " " + orphaSigns.get(frequency) + " ";
                    orphaSigns.put(frequency, myList);
                } else {
                    orphaSigns.put(frequency, myList);
                }

            }

            // myDisease.setOrpha_signs(orphaSigns);
            // myDisease.displayOrpha();
            //orphaSigns.clear();
        }
    }


    protected String send(String method, URL service, JSONObject object) throws Exception{
        //System.out.println("send function called");
        HttpURLConnection connection =  (HttpURLConnection) service.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        if (object != null) this.writeBody(connection, object);
        String result = this.readBody(connection);
        //			System.err.println(result); //DEBUG
        this.checkError(connection);
        return result;
    }

    protected void writeBody(HttpURLConnection connection, JSONObject object) throws Exception
    {
        //System.out.println("writeBody called");
        connection.setDoOutput(true);
        OutputStreamWriter writer =
                new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        object.write(writer);
        writer.close();
    }

    /**
     * @return response body
     */
    protected String readBody(HttpURLConnection connection) throws Exception {
        //System.out.println("read function called");
        InputStream str = connection.getInputStream();
        //System.out.println("read - 0");
        InputStream buffy = new BufferedInputStream(str);
        BufferedReader reader = new BufferedReader(new InputStreamReader(buffy));
        //System.out.println("read - 1");
        String line = reader.readLine();
        //System.out.println("read - 2");
        StringBuilder result = new StringBuilder();
        //System.out.println("read - 3");
        while (line != null) {
            result.append(line);
            line = reader.readLine();
        }
        //System.out.println("read end");
        //System.out.println(result);
        return result.toString();
    }

    protected void checkError(HttpURLConnection connection) throws Exception {
        //System.out.println("check called");
        connection.disconnect();
        int code = connection.getResponseCode();
        //			System.err.println(code); //DEBUG
        if (code/100 != 2) throw new HTTPException(code);
    }

    public void deleteDocument(String id, String rev) {

        URL serverURLPath = null;
        HttpURLConnection connection = null;

        try {
            serverURLPath = new URL(host + "/" + db + "/" + id + "?rev=" + rev);

        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        try {
            connection = (HttpURLConnection)serverURLPath.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(false);

            connection.getInputStream();

            if(connection.getResponseCode() != 200) {
                throw new RuntimeException("Document not deleted.");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            // Release the connection.
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

}
