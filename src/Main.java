import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        String apiKey = "73e256941e25bd51d6750e8f02fac85c";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a city : ");
        String city = scanner.nextLine();
        System.out.print("Enter a country code : ");
        String countryCode = scanner.nextLine();

        Request request = new Request(apiKey, city, countryCode);

        request.getResults();
    }
}