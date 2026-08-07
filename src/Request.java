import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Request {
    String apiKey;
    String city;
    String countryCode;

    Request(String apiKey, String city, String countryCode) {
        this.apiKey = apiKey;
        this.city = city;
        this.countryCode = countryCode;
    }

    void getResults() throws IOException, ParseException {

        double[] geolocation = getGeolocation();


        String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" +
                geolocation[0] + "&lon=" + geolocation[1] + "&units=metric&appid=" + apiKey;
        URL urlObj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if  (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Successfully connected to OpenWeather Weather API : " + responseCode);

            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }

            System.out.println("Data : \n"  + sb);

        }

        else {
            System.out.println("Failed to get data OpenWeather Weather : " + responseCode);
            System.out.println(url);
        }

    }

    double[] getGeolocation() throws IOException, ParseException {

        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" +
                city + "," + countryCode + "," +
                "&limit=1" + "&appid=" + apiKey;

        URL urlObj = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Successfully connected to OpenWeather Geolocation API : " + responseCode);

            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());

            while (scanner.hasNext()){
                sb.append(scanner.nextLine());
            }

            JSONParser parser  = new JSONParser();
            Object obj = parser.parse(sb.toString());

            JSONArray array = (JSONArray) obj;
            JSONObject object = (JSONObject) array.get(0);

            //System.out.println("Lat: " + object.get("lat"));
            //System.out.println("Lon: " + object.get("lon"));

            double lat  = (Double) object.get("lat");
            double lon = (Double) object.get("lon");

            double[] geolocation = {lat,lon};

            return geolocation;
        }
        else {
            System.out.println("Failed connected to OpenWeather Geolocation API : " + responseCode);
            System.out.println(url);
        }

        return null;
    }
}
