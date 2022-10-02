package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static final String DEFAULT_RESPONSE = "OK";


    public EngineRequestHTTPServer(int port) {
        this.port = port;
    }

    private void handleRequest(String requestPath, String query) {
        //System.out.println(requestPath);
        //System.out.println("QUERY"+query);


        if(query.equals("electric"))
        {   System.out.println("HTTP GET request has been received for \"electric\"");
            EngineType x= EngineType.ELECTRIC;
            EngineFactory engineFactory = new EngineFactory();
            Engine engine= engineFactory.produceEngine(x);
        }

        else if(query.equals("gas"))
        {   System.out.println("HTTP GET request has been received for \"gas\"");
            EngineType x= EngineType.ELECTRIC;
            EngineFactory engineFactory = new EngineFactory();
            Engine engine= engineFactory.produceEngine(x);
        }

        else if(query.equals("diesel"))
        {   System.out.println("HTTP GET request has been received for \"diesel\"");
            EngineType x= EngineType.ELECTRIC;
            EngineFactory engineFactory = new EngineFactory();
            Engine engine= engineFactory.produceEngine(x);

        }
        else {
            System.out.println("query is invalid");

        }










        //Your code goes here
    }

        public void stopServer () {
            if (server != null)
                server.stop(1);
        }

        public void startServer () {

            try {
                //Create a new HTTPServer instance attached to given port, not in listening state
                server = HttpServer.create(new InetSocketAddress("localhost", port), 1);
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
                    handleRequest(requestURI.getPath(), requestURI.getQuery());

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
