package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.File;
import java.io.IOException;

public class Test {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static EntityTransaction transaction = entityManager.getTransaction();

    public static void main(String[] args) throws Exception {
    //Data first = new Data();

        //Create a file that will hold json code from the api
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


            //Connect to said api
            URI uri = new URI("https://archive-api.open-meteo.com/v1/archive?latitude=52.52&longitude=13.41&start_date=2024-11-07&end_date=2024-11-07&hourly=temperature_2m");
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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

            //Write the api code to the file
            FileWriter myWriter = new FileWriter("filename.json");
            myWriter.write(String.valueOf(responseStrBuilder));
            myWriter.close();


            JSONObject obj = new JSONObject(responseStrBuilder.toString());

            JSONObject array2 = obj.getJSONObject("hourly");

            JSONArray tempObject = array2.getJSONArray("temperature_2m");
            JSONArray dateObject = array2.getJSONArray("time");

            System.out.println("\n");
            System.out.println("START HERE");
            System.out.println(dateObject);
            System.out.println(tempObject);

            for (int i = 0; i < tempObject.length(); i++) {
                try {
                    transaction.begin();
                    //first.setPoint(String.valueOf(tempObject));
                    //first.setDate(String.valueOf(dateObject));
                    Data first = new Data();
                    BigDecimal tempObject1 = tempObject.getBigDecimal(i);
                    String hi = tempObject1.toString();
                    first.setPoint(hi);

                    entityManager.persist(first);
                    transaction.commit();

                } finally {
                    if (transaction.isActive()) {
                        transaction.rollback();
                    }
                }

            }

            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }

