package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "OK";
    private EngineFactory ef;



    public EngineRequestHTTPServer(int port, EngineFactory pEf) {
        this.port = port;
        this.ef = pEf;
    }

    private String handleRequest(String requestPath, String query){
        System.out.println(requestPath);
        System.out.println(query);

        String engineType = query.split("=")[1].toUpperCase();
        System.out.println(engineType);


        if(requestPath.equalsIgnoreCase("/order")){
            ef.produceEngine(EngineType.valueOf(engineType));
        }
        //Your code goes here
        String response = engineType + " engine is produced";
        return response;
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
                String r = handleRequest(requestURI.getPath(),requestURI.getQuery());

                //Create a default response message OK
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + r + "</html>";
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
