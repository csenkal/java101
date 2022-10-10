package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "OK";

    EngineFactory D1 = new EngineFactory();
    DieselEngine D2 = new DieselEngine();

    public EngineRequestHTTPServer(int port) {
        this.port = port;
    }

    private void handleRequest(String requestPath, String query){

        String query2 = query.substring(query.lastIndexOf("=") + 1); // parse işlemini .useDelimiter("="); ile yapmaya çalıştım fakat çok karıştı

        if (requestPath.equals("/stop")){
            server.stop(1);
        }

        //System.out.println(requestPath);
        //System.out.println(query);

        if (query2.equals("GAS")){
            D1.produceEngine("GAS");
        }
        else if (query2.equals("DIESEL")){
            D1.produceEngine("DIESEL");
            System.out.println("yoo");
        }
        else if (query2.equals("ELECTRIC")){
            D1.produceEngine("ELECTRIC");
        }
        else {
            server.stop(1);
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
