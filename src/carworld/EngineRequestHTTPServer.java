package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "COULD NOT FIND THE RELEVANT ENGINE TYPE";
    private  HttpExchange theExchange; //Global variable for this class

   private  EngineFactory theEngineFactory;


    public EngineRequestHTTPServer(int port) {
        this.port = port;

    }

    public void setTheEngineFactory(EngineFactory theEngineFactory) {
        this.theEngineFactory = theEngineFactory;
    }

    private void handleRequest(String requestPath, String query){
        System.out.println(requestPath);
        System.out.println(query);

        //Your code goes here
        switch (requestPath){
            // An order
            case "/order":
                handleOrder(query);
                break;

            case "/stop":
                stopServer();
                break;

            default:
                // Print statement corresponding case
                System.out.println("no match for path");

        }

    }

    /**
     * This function is used to handle the order requests
     * @param query : Query string from http request
     */
    public void handleOrder(String query){

        if (query!=null && query.split("=").length == 2) {
            String s = query.split("=")[1];
            System.out.println(s);
            try {
                if(s!=null){
                    EngineType engineType = EngineType.valueOf(s);
                    Engine newEngine = theEngineFactory.produceEngine(engineType);
                    //Create response message
                    String theMessage = s + " Engine is created " + newEngine.toString();
                    String response = "<html><link rel=\"icon\" href=\"data:,\">" + theMessage + "</html>";
                    //Set 200 as request response CODE
                    theExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                    //Write the response to the response OutputStream
                    theExchange.getResponseBody().write(response.getBytes());
                    //Close response output stream
                    theExchange.getResponseBody().close();
                } else{
                    //Create response message
                    String theMessage = "Bad Input !!";
                    String response = "<html><link rel=\"icon\" href=\"data:,\">" + theMessage + "</html>";
                    //Set 200 as request response CODE
                    theExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                    //Write the response to the response OutputStream
                    theExchange.getResponseBody().write(response.getBytes());
                    //Close response output stream
                    theExchange.getResponseBody().close();

                }

            } catch (IllegalArgumentException | IOException e) {
                System.out.println("Illegal argument is caught!!");
            }
        } else{
            try{
                //Create response message
                String theMessage = "Bad Input !!";
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + theMessage + "</html>";
                //Set 200 as request response CODE
                theExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                //Write the response to the response OutputStream
                theExchange.getResponseBody().write(response.getBytes());
                //Close response output stream
                theExchange.getResponseBody().close();
            }catch(IllegalArgumentException | IOException e){
                System.out.println("Illegal argument is caught!!");
            }


        }


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
                theExchange = exchange;
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
