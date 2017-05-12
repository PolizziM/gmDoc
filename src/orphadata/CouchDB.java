package orphadata;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.json.simple.JSONValue;

import com.fourspaces.couchdb.Database;

import com.fourspaces.couchdb.Document;

import com.fourspaces.couchdb.Session;

import com.fourspaces.couchdb.ViewResults;

import application.model.*;

public class CouchDB {

	static Session dbSession ;

	static Database db;

	public static void main(String[] args) {

		connect();
		getTotalDocumentCount();

//		viewAllDocuments();
//		viewDiseaseBySign(new Sign(0,"Abnormal colour of the urine/cholic/dark urines".replaceAll(" ", "%20")));
//		viewsDemo();

		//        deleteDocument("6");

		//        getTotalDocumentCount();

		//        deleteDatabase(dbName);

	}
	public static void connect(){
		String dbName = "orphadatabase";

		dbSession = new Session("couchdb.telecomnancy.univ-lorraine.fr", 80,"ho9u","CouchDB2A");
		db=dbSession.getDatabase(dbName);
	}

	public static void createDatabase(String dbName){

		dbSession = new Session("localhost", 5984);

		db = dbSession.createDatabase(dbName);

		if(db==null)

			db = dbSession.getDatabase(dbName);

	}

	public static Document getDocument(String id,String name,String group,String designation,String language){

		Document doc = new Document();

		doc.setId(id);

		doc.put("EmpNO", id);

		doc.put("Name", name);

		doc.put("Group", group);

		doc.put("Designation", designation);

		doc.put("Language", language);

		return doc;

	}


	public static int getTotalDocumentCount(){

		int count = db.getDocumentCount();

		System.out.println("Total Documents: " + count);

		return count;

	}


	public static List<Disease> viewDiseaseBySign(Sign s){
		List<Disease> res = new ArrayList<Disease>();
		Document doc = null;

		try {

			doc = db.getDocument("_design/clinicalsigns");

		} catch (Exception e1) {

			doc = null;

		}
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpGet get = new HttpGet("http://couchdb.telecomnancy.univ-lorraine.fr:80/orphadatabase/_design/clinicalsigns/_view/GetDiseaseByClinicalSign?key=%22"+s.getName().replaceAll(" ", "%20")+"%22");

			HttpResponse response = httpclient.execute(get);

			HttpEntity entity=response.getEntity();

			InputStream instream = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));

			String strdata = null;

			String jsonString = "" ;

			while( (strdata =reader.readLine())!=null)

			{

//				                   System.out.println(strdata);

				jsonString += strdata;

			}

//			System.out.println("Json String: " + jsonString);

			Map<String, Object> jsonMap = getMapFromJsonString(jsonString);

			if(jsonMap!=null)

			{

//				System.out.println("total_rows: " + jsonMap.get("total_rows"));

//				System.out.println("offset: " + jsonMap.get("offset"));

				List<Map> rowsList = (List<Map>) jsonMap.get("rows");

				if(rowsList!=null)

				{

					for(Map row: rowsList)

					{

//						System.out.println("Value: " + row.get("value"));

						int orphaNumber = Integer.parseInt(((Map)((Map)row.get("value")).get("disease")).get("OrphaNumber").toString());
						String names = ((Map)((Map) ((Map)row.get("value")).get("disease")).get("Name") ).get("text").toString() ;
						String[] name=names.split("/");
						for(int k = 0;k<name.length;k++)
						{
							Disease d = new Disease(orphaNumber,name[k]);
							res.add(d);
						}


//						System.out.println("Name: " + ((Map)((Map) ((Map)row.get("value")).get("disease")).get("Name") ).get("text")  );
//						System.out.println("Name: " + ((Map)((Map) ((Map)row.get("value")).get("disease")).get("Name") ).get("text")  );
//						System.out.println("_id: " + ((Map)((Map)row.get("value")).get("disease")).get("OrphaNumber"));
//
//						System.out.println("Language: " + ((Map)row.get("value")).get("Language"));
//
//						System.out.println("EmpNO: " + ((Map)row.get("value")).get("EmpNO"));
//
//						System.out.println("Designation: " + ((Map)row.get("value")).get("Designation"));
//
//						System.out.println("Group: " + ((Map)row.get("value")).get("Group"));

					}

				}

			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IllegalStateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}  
		return res;
	}
	public static void viewAllDocuments(){

		ViewResults results = db.getAllDocuments();

		List<Document> documentsList = results.getResults();

		if(documentsList!=null)

		{

			for(Document doc : documentsList)

			{

				System.out.println(doc.get("id") + " : " +  doc);

			}

		}

	}

	public static void viewsDemo(){

		if(db!=null)

		{

			Document doc = null;

			try {

				doc = db.getDocument("_design/couchview");

			} catch (Exception e1) {

				doc = null;

			}

			try {

				if(doc==null)

				{

					doc = new Document();

					doc.setId("_design/couchview");

					String str = "{\"javalanguage\": {\"map\": \"function(doc) { if (doc.Language == 'Java')  emit(null, doc) } \"}, \"java_and_se\": {\"map\": \"function(doc) { if (doc.Language == 'Java' & doc.Designation == 'SE')  emit(null, doc) } \"}}";

					doc.put("views", str);

					db.saveDocument(doc);

				}

			} catch (Exception e) {

			}

		}

		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpGet get = new HttpGet("http://localhost:5984/foodb/_design/couchview/_view/javalanguage");

			HttpResponse response = httpclient.execute(get);

			HttpEntity entity=response.getEntity();

			InputStream instream = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));

			String strdata = null;

			String jsonString = "" ;

			while( (strdata =reader.readLine())!=null)

			{

				//                   System.out.println(strdata);

				jsonString += strdata;

			}

			System.out.println("Json String: " + jsonString);

			Map<String, Object> jsonMap = getMapFromJsonString(jsonString);

			if(jsonMap!=null)

			{

				System.out.println("total_rows: " + jsonMap.get("total_rows"));

				System.out.println("offset: " + jsonMap.get("offset"));

				List<Map> rowsList = (List<Map>) jsonMap.get("rows");

				if(rowsList!=null)

				{

					for(Map row: rowsList)

					{

						System.out.println("----------------");

						System.out.println("Id: " + row.get("id"));

						System.out.println("Value: " + row.get("value"));

						System.out.println("Name: " + ((Map)row.get("value")).get("Name"));

						System.out.println("_id: " + ((Map)row.get("value")).get("_id"));

						System.out.println("Language: " + ((Map)row.get("value")).get("Language"));

						System.out.println("EmpNO: " + ((Map)row.get("value")).get("EmpNO"));

						System.out.println("Designation: " + ((Map)row.get("value")).get("Designation"));

						System.out.println("Group: " + ((Map)row.get("value")).get("Group"));

					}

				}

			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IllegalStateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}    

	}

	public static Map<String, Object> getMapFromJsonString(String jsonString){

		Map<String, Object> jsonMap = (Map<String, Object>) JSONValue.parse(jsonString);

//		System.out.println("Json Map: " + jsonMap);

		return jsonMap;

	}
}