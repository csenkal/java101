package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

public class EngineRequestHTTPServer {
    private HttpServer server;
    private int port;
    private static  String DEFAULT_RESPONSE = "OK";

    private  EngineFactory ef;

    public EngineRequestHTTPServer(int port, EngineFactory pEf ) {
        this.port = port;
        this.ef=pEf;



    }

    private void handleRequest(String requestPath, String query) {
        System.out.println(requestPath);
        System.out.println("QUERY"+query);



        EngineFactory engineFactory1 = new EngineFactory(); //

        if(requestPath.equalsIgnoreCase("/order")){
            if(query.equalsIgnoreCase("type=gas")){
                ef.produceEngine(EngineType.GAS);
                DEFAULT_RESPONSE= "HTTP GET request has been received for gas";

            }
            else if(query.equalsIgnoreCase("type=diesel")){
                ef.produceEngine(EngineType.DIESEL);
                DEFAULT_RESPONSE= "HTTP GET request has been received for diesel";
            }
            else if(query.equalsIgnoreCase("type=electric")){
                ef.produceEngine(EngineType.ELECTRIC);
                DEFAULT_RESPONSE= "HTTP GET request has been received for electric";
            }
            else {
                System.out.println("query is invalid");
                DEFAULT_RESPONSE= "query is invalid";
            }


        }




/*

bu kod neden düzgün çalışmıyor araştırılacak.


        if(query.equalsIgnoreCase("type=electric"))
        {   System.out.println("HTTP GET request has been received for \"electric\"");
            EngineType engineType= EngineType.ELECTRIC;
            Engine engine= engineFactory1.produceEngine(engineType);
            ((ElectricEngine) engine).getBrakeEnergyRegenerator();
            DEFAULT_RESPONSE= "HTTP GET request has been received for ELECTRIC";

        }

        else if(query.equalsIgnoreCase("type=gas"))
        {   System.out.println("HTTP GET request has been received for \"gas\"");
            EngineType engineType= EngineType.ELECTRIC;
            Engine engine= engineFactory1.produceEngine(engineType);
            ((GasEngine) engine).getSparkPlug();
            DEFAULT_RESPONSE= "HTTP GET request has been received for ELECTRIC";

        }

        else if(query.equalsIgnoreCase("type=diesel"))
        {   System.out.println("HTTP GET request has been received for \"diesel\"");
            EngineType engineType= EngineType.ELECTRIC;
            Engine engine= engineFactory1.produceEngine(engineType);
            ((DieselEngine) engine).getParticleFilter();

            DEFAULT_RESPONSE= "HTTP GET request has been received for ELECTRIC";


        }
        else {
            System.out.println("query is invalid");
            DEFAULT_RESPONSE= "query is invalid";

        }

*/



    }










        //Your code goes here


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
