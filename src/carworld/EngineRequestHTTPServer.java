package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private static String DEFAULT_RESPONSE;

    public static void setDefaultResponse(String defaultResponse) {
        DEFAULT_RESPONSE = defaultResponse;
    }

    public static String getDefaultResponse() {
        return DEFAULT_RESPONSE;
    }

    private HttpServer server;
    private int port;

    private EngineFactory ef;


    public EngineRequestHTTPServer(int port, EngineFactory pEf) {
        this.port = port;
        this.ef = pEf;
    }

    private void handleRequest(String requestPath, String query) {
        System.out.println(requestPath);
        System.out.println(query);


        if (requestPath.equalsIgnoreCase("/order/type=gas")) {
            // if(query.equalsIgnoreCase("type=gas")){
            ef.produceEngine(EngineType.GAS);
            setDefaultResponse("GAS ENGINE IS PRODUCED");

        } else if (requestPath.equalsIgnoreCase("/order/type=diesel")) {
            ef.produceEngine(EngineType.DIESEL);
            setDefaultResponse("DIESEL ENGINE IS PRODUCED");

        } else if (requestPath.equalsIgnoreCase("/order/type=electric")) {
            ef.produceEngine(EngineType.ELECTRIC);
            DEFAULT_RESPONSE = "ELECTRIC ENGINE IS PRODUCED";
        }

    }


        //Your code goes here




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
                handleRequest(requestURI.getPath(),requestURI.getPath());


                //Create a default response message OK
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + getDefaultResponse() + "</html>";
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
        System.out.println("Engine Request HTTP Server is started listening at port:"+port);
    }



}