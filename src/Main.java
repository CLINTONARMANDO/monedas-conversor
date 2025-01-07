import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String key = "0944bb3667089c7a776e6780";
        String mon1 = "";
        String mon2 = "";
        Gson gson = new Gson();

        while (true) {
            System.out.println("Bienvenidos al conversor de monedas");
            System.out.println("1) Dolar -> Soles");
            System.out.println("2) Soles -> Dolar");
            System.out.println("3) Euro -> Soles");
            System.out.println("4) Soles -> Euro");
            System.out.println("5) Yen Japones -> Soles");
            System.out.println("6) Soles -> Yen Japones");
            System.out.println("7) Libra esterlina -> Soles");
            System.out.println("8) Soles -> Libra esterlina");
            System.out.println("9) Salir");
            System.out.println("Ingrese un una opcion");
            String op = sc.nextLine();
            switch (op) {
                case "1":
                    mon1 = "USD";
                    mon2 = "PEN";
                    break;
                case "2":
                    mon1 = "PEN";
                    mon2 = "USD";
                    break;
                case "3":
                    mon1 = "EUR";
                    mon2 = "PEN";
                    break;
                case "4":
                    mon1 = "PEN";
                    mon2 = "EUR";
                    break;
                case "5":
                    mon1 = "JPY";
                    mon2 = "PEN";
                    break;
                case "6":
                    mon1 = "PEN";
                    mon2 = "JPY";
                    break;
                case "7":
                    mon1 = "GBP";
                    mon2 = "PEN";
                    break;
                case "8":
                    mon1 = "PEN";
                    mon2 = "GBP";
                    break;
                case "9":
                    System.out.println("¡Gracias por usar el conversor!");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    continue;
            } System.out.print("Ingrese el monto a convertir: ");
            double monto;
            try {
                monto = Double.parseDouble(sc.nextLine().trim());
                if (monto <= 0) {
                    System.out.println("El monto debe ser mayor que 0. Intente de nuevo.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Monto inválido. Intente de nuevo.");
                continue;
            }

            try {
                HttpClient client = HttpClient.newHttpClient();
                String url = "https://v6.exchangerate-api.com/v6/" + key + "/pair/" + mon1 + "/" + mon2;
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                String resposta = httpResponse.body();

                // Deserializar respuesta JSON
                ExchangeRateResponse response = gson.fromJson(resposta, ExchangeRateResponse.class);

                if ("success".equalsIgnoreCase(response.getResult())) {
                    double conversionRate = response.getConversionRate();
                    double convertedAmount = monto * conversionRate;
                    System.out.printf("El monto convertido de a es: %.2f %s%n", convertedAmount, mon2);
                    Thread.sleep(2000);
                } else {
                    System.out.println("Error en la API: " + response.getResult());
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error al realizar la conversión: " + e.getMessage());
            }

        }

    }
}