import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class CalculatorServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/add", new AddHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started at http://localhost:8000");
    }

    static class AddHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                int a = Integer.parseInt(params[0].split("=")[1]);
                int b = Integer.parseInt(params[1].split("=")[1]);
                int result = a + b;
                String response = "Result: " + result;
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}