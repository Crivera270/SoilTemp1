package org.example;

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


public class Test3 {
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

            URI uri = new URI("https://api.meteomatics.com/2024-11-03T00:00:00Z/t_2m:C/52.520551,13.461804/json");
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
            //Object obj = new JSONParser().parse(new FileReader("filename.json"));

            JSONObject obj = new JSONObject(responseStrBuilder.toString());

            //simple string parse
            String version = obj.getString("version");
            System.out.println("version is: " + version);

            //starting at data
            JSONArray array = obj.getJSONArray("data");
            System.out.println("data is: " + array);

            //Getting json objects inside array parameter
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj4 = array.getJSONObject(i);
                //Getting id and type of json objects inside array2
                System.out.println("PARAMETER: " + obj4.getString("parameter"));
            }

            //coordinates
            JSONArray array1 = obj.getJSONArray("data");
            System.out.println("data is: " + array1);

            //got inside of coordinates, not to get inside dates
            for (int i = 0; i < array1.length(); i++) {
                JSONObject obj4 = array.getJSONObject(i);
                //Getting id and type of json objects inside array2
                System.out.println("Coordinates:" + obj4.getJSONArray("coordinates"));
            }

            //try to get inside coordinates

            JSONArray array2 = obj.getJSONArray("data");
            System.out.println("data is: " + array2);



            for (int i = 0; i < array2.length(); i++) {
                JSONObject obj7 = array2.getJSONObject(i);

                System.out.println("Coordinates:" + obj7.getJSONArray("coordinates"));
                JSONArray coordinatesArray = obj7.getJSONArray("coordinates");
                for (int j = 0; j < coordinatesArray.length(); j++) {
                    JSONObject coordinate = coordinatesArray.getJSONObject(j);
                    JSONArray datesArray = coordinate.getJSONArray("dates");

                    for (int k = 0; k < datesArray.length(); k++) {
                        JSONObject dateObject = datesArray.getJSONObject(k);
                        String date = dateObject.getString("date");
                        double value = dateObject.getDouble("value");

                        // Output the date and value
                        System.out.println("Date: " + date + ", Value: " + value);

                    }
                }




            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
