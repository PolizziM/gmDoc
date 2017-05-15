package couchDB;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import application.model.*;
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
import java.util.List;
import java.util.Map.Entry;



/**
 * Created by meh on 10/05/2017.
 */

public class ConnectOrpha {
	public static void main(String[] args) {
		List<Disease> l = new ArrayList<Disease>() ;
		List<Disease> l2 = new ArrayList<Disease>(); 
		ConnectOrpha c = new ConnectOrpha();
		try {
			l = c.getDiseaseBySign("Abnormal colour of the urine");
			System.out.println(l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			for(Disease d : l){
//				l2 = c.getDiseaseByName(d.getNom());
//				System.out.println(l2);
//			}
//
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		
//		ArrayList<Disease> l;
//		try {
//			l = c.getDiseaseByName("m");
//			System.out.println(l);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

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
//        System.out.println(url +"?key=\"" +Character.toUpperCase(disease.charAt(0)) + disease.substring(1).toLowerCase() +"\"");
        JSONArray rows = myJson.getJSONArray("rows");
        ArrayList<Disease> DiseaseesOrpha = new ArrayList<Disease>();

//        System.out.println("rows: " + rows);

        if (rows.length() > 0) {
            for (int i = 0; i < rows.length(); i++) {
                Disease myDisease = new Disease();
                if (rows.getJSONObject(i).getString("key").contains(disease)) {

                    JSONObject global = rows.getJSONObject(i);
                    JSONObject allValues = global.getJSONObject("value");

                    //Disease name
                    myDisease.setName(allValues.getJSONObject("Name").getString("text"));

                    //OrphaNumber
                    int no =0;
                    myDisease.setId(allValues.getInt("OrphaNumber"));
                    
					//ID OMIM
					try{
						JSONObject externalRef = allValues.getJSONObject("ExternalReferenceList");
						if(externalRef.getInt("count") == 1){
							String refer = externalRef.getJSONObject("ExternalReference").getString("Source");
							if(refer.equals("OMIM")){
								myDisease.setId(externalRef.getJSONObject("ExternalReference").getInt("Reference"));
							}
						}else if (externalRef.getInt("count") > 1){
							JSONArray externalTab = externalRef.getJSONArray("ExternalReference");
							Boolean b = true;
							for(int j = 0; j<externalTab.length();j++){
								String refer = externalTab.getJSONObject(j).getString("Source");
								if(refer.equals("OMIM") && b){
									myDisease.setId(externalTab.getJSONObject(j).getInt("Reference"));
								}
							}
						}

					}catch(Exception e){
						e.printStackTrace();
					}
					
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
	public List<Disease> getDiseaseBySign(String sign) throws Exception{
		String url = "/_design/clinicalsigns/_view/GetDiseaseByClinicalSign";
		JSONObject myJson = get(url +"?startkey=\"" +sign.toLowerCase() +"\"&endkey={}&descending=false");
		HashMap<Integer, String> matchedDiseases = new HashMap<Integer,String>();
		ArrayList<Disease> res = new ArrayList<Disease>();
		JSONArray rows = myJson.getJSONArray("rows");
		if(rows.length() > 0){
			for(int i = 0; i<rows.length(); i++){
				JSONObject row = rows.getJSONObject(i);
				if(row.getString("key").contains(sign)){
					JSONObject value = row.getJSONObject("value");
					JSONObject disease = value.getJSONObject("disease");
					int orphaNumber = disease.getInt("OrphaNumber");
					String diseaseName = disease.getJSONObject("Name").getString("text");
					if(!matchedDiseases.containsValue(orphaNumber)){
						matchedDiseases.put(orphaNumber, diseaseName);
						Disease d = new Disease(orphaNumber, diseaseName);
		
						res.add(d);
					}
				}
			}
		}else{
			System.out.println("The referenced sign doesn't match with any disease");
		}
//		for(Entry<Integer, String> entry : matchedDiseases.entrySet()) {
//			System.out.println("OrphaNumber : " +entry.getKey() +"	Disease name : " +entry.getValue());
//		}
		return res;
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
