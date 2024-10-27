package org.example;// run on the command line as:
// javac MeteomaticsExample.java
// java MeteomaticsExample username password

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Test2 {
    public static void main(String[] args) throws Exception {
        try {

            File file = new File("filename.json");
            try {
                File myObj = new File("filename.json");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            //  String username = args[0];
            //  String password = args[1];

            // System.out.print("username: " + username + ", password: " + password + "\n");

            URI uri = new URI("https://api.meteomatics.com/2024-10-20T00:00:00Z/t_2m:C/52.520551,13.461804/json");
            URL url = uri.toURL();
            String credentials = "valenciacollege_rivera_christina" + ":" + "VV2hU5du3q";
            String encoding = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + encoding);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            System.out.print(responseStrBuilder.toString());

            //try
            FileWriter myWriter = new FileWriter("filename.json");
            myWriter.write(String.valueOf(responseStrBuilder));
            myWriter.close();

            //super try JSON PARSING
            Object obj = new JSONParser().parse(new FileReader("filename.json"));
           JSONObject jo = (JSONObject) obj;



          JSONArray ja = (JSONArray) jo.get("data");
          System.out.println("DATA HERE:" + ja);




            //try #2



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
