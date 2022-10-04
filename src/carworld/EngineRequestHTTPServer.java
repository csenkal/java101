package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "COULD NOT FIND THE RELEVANT ENGINE TYPE";


   private  EngineFactory theEngineFactory;


    public EngineRequestHTTPServer(int port) {
        this.port = port;

    }

    public void setTheEngineFactory(EngineFactory theEngineFactory) {
        this.theEngineFactory = theEngineFactory;
    }

    private String handleRequest(String requestPath, String query){
        System.out.println(requestPath);
        System.out.println(query);

        //Your code goes here
        switch (requestPath){
            // An order
            case "/order":
                return handleOrder(query);


            case "/stop":
                stopServer();
                return "server stopped";

            default:
                // Print statement corresponding case
                return "no match for path";

        }

    }

    /**
     * This function is used to handle the order requests
     * @param query : Query string from http request
     * @return the message to be displayed in the http response
     */
    public String handleOrder(String query){
        String theMessage=null;

        String[] split;
        if (query!=null && (split=query.split("=")).length == 2) {
            String typeStr = split[0];
            String engineTypeStr = split[1];
            System.out.println(engineTypeStr);
            try {
                if(typeStr!=null && typeStr.equals("type") && engineTypeStr!=null){
                    EngineType engineType = EngineType.valueOf(engineTypeStr);
                    Engine newEngine = theEngineFactory.produceEngine(engineType);
                    //Create response message
                    theMessage = engineTypeStr + " Engine is created " + newEngine.toString();

                } else{
                    //Create response message
                    theMessage = "Bad Input !!";
                }

            } catch (IllegalArgumentException ex) {
                System.out.println("Illegal argument is caught!!");
            }
        } else{
            theMessage = "Bad Input !!";
        }

        return theMessage;
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
                String message = handleRequest(requestURI.getPath(),requestURI.getQuery());

                //Create a default response message OK
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + message + "</html>";
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
        System.out.printf("Engine Factory HTTP Server is listening at "+port);
    }



}
