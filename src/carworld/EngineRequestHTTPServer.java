package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "OK";
    private static String RESPONSE="OK";


    public EngineRequestHTTPServer(int port) {
        this.port = port;
    }

    private void handleRequest(String requestPath, String query) {

        RESPONSE=DEFAULT_RESPONSE;
        System.out.println(requestPath);
        System.out.println(query);

        EngineFactory ef = new EngineFactory();

        if (requestPath.equals("/stop")) {
            stopServer();
        }

        else {

                if (query != null && query.equals("type=GAS")) {
                    ef.produceEngine(EngineType.GAS);
                    RESPONSE = "GAS engine has been produced";
                } else if (query != null && query.equals("type=DIESEL")) {
                    ef.produceEngine(EngineType.DIESEL);
                    RESPONSE = "DIESEL engine has been produced";
                } else if (query != null && query.equals("type=ELECTRIC")) {
                    ef.produceEngine(EngineType.ELECTRIC);
                    RESPONSE = "ELECTRIC engine has been produced";
                } else {

                    RESPONSE = DEFAULT_RESPONSE;

                }
            }
        }

    public void stopServer(){
        if(server!=null)
            server.stop(1);
        System.out.println("server kapandı");
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
                String response = "<html><link rel=\"icon\" href=\"data:,\">" + RESPONSE + "</html>";
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