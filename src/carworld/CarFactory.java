package carworld;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;


public class CarFactory {


	public static void main (String[] args) {
		EngineRequestHTTPServer server = new EngineRequestHTTPServer(8085);
		server.startServer();

		/*
		Engine myEngine = new Engine();
		Transmission myTransmission = new Transmission();
		
		Wheel[] myWheels = new Wheel[4];
		
		for (int i = 0; i<myWheels.length; i++){
			myWheels[i] = new Wheel();
		}
	
	
		Car myCar = new Car(myEngine, myTransmission, myWheels);
		
		myCar.start();
		myCar.setThrottle(0.2f);
		myCar.changeGear((short)2);
		myCar.stop();
		myCar.setThrottle(1);

		*/
    }
}







