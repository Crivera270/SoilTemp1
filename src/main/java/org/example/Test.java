package org.example;// run on the command line as:
// javac MeteomaticsExample.java
// java MeteomaticsExample username password

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.json.*;

public class Test {
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

            URI uri = new URI("https://archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41&start_date=2024-11-07&end_date=2024-11-07&hourly=temperature_2m");
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



//11/22/2024 try
            org.json.JSONObject obj = new JSONObject(responseStrBuilder.toString());

//try to get some data
          // worked --> JSONObject array2 = obj.getJSONObject("hourly");
          //  System.out.println("data is: " + array2);


//try again --->
            JSONObject array2 = obj.getJSONObject("hourly");

            //for (int k = 0; k < array2.length(); k++) {
                JSONArray dateObject = array2.getJSONArray("temperature_2m");
                JSONArray dateObject1 = array2.getJSONArray("time");
               // String date = dateObject.getString("time");
               // double value = dateObject.getDouble("temperature_2m");

                // Output the date and value
               // System.out.println("Date: " + date + ", Value: " + value);
                System.out.println("\n");
                System.out.println("START HERE");
                System.out.println(dateObject1);
                System.out.println(dateObject);

          //  }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
