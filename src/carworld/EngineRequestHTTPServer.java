package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private EngineFactory ef;
    private static String DEFAULT_RESPONSE = "OK";

    public EngineRequestHTTPServer(int port, EngineFactory pEf) {
        this.port = port;
        this.ef = pEf;
    }


    private void handleRequest(String requestPath, String query){
        System.out.println(requestPath);
        System.out.println(query);

        String query2 = query.substring(query.lastIndexOf("=") + 1);
        // parse işlemini .useDelimiter("="); ile yapmaya çalıştım fakat çok karıştı

        if(requestPath.equalsIgnoreCase("/order")){
            if(query2.equalsIgnoreCase("gas")){
                ef.produceEngine(EngineType.GAS);
                DEFAULT_RESPONSE = "Gasoline engine produced";
            }
            else if(query2.equalsIgnoreCase("diesel")){
                ef.produceEngine(EngineType.DIESEL);
                DEFAULT_RESPONSE = "Diesel Engine produced";
            }
            else if(query2.equalsIgnoreCase("electric")){
                ef.produceEngine(EngineType.ELECTRIC);
                DEFAULT_RESPONSE = "Electric engine produced";
            }
            else
                DEFAULT_RESPONSE = "Wrong order";


        }
        //Your code goes here


    }

    public void stopServer(){
        if(server!=null)
            server.stop(1);
    }

    public void startServer(){

        try {
            //Create a new HTTPServer instance attached to given port, not in listening state
            server = HttpServer.create(new InetSocketAddress("localhost",port),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Define http url to listen
        HttpContext context = server.createContext("/");
        //Attach a handler to the context
        context.setHandler(new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                URI requestURI = exchange.getRequestURI();

                //Pass request path and query to handleRequest Method
                handleRequest(requestURI.getPath(),requestURI.getQuery());

                //Create a default response message OK
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + DEFAULT_RESPONSE + "</html>";
                //Set 200 as request response CODE
                exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                //Write OK to the response OutputStream
                exchange.getResponseBody().write(response.getBytes());
                //Close response output stream
                exchange.getResponseBody().close();
            }
        });
        //Actually start the http server.
        //It runs in a different thread other than main
        //So when the main comes to the last line, process does not terminate
        //Until you explicitly stop server
        server.start();
    }



}
