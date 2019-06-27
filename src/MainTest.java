import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OSRA osra = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		try {
			osra = new OSRA("onos", "rocks", "localhost", "localhost");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Socket socket = osra.openTCPSocket("localhost", 8080, 100000, 1000000);
			

//			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			salida = new PrintWriter(new BufferedWriter(new 
//					OutputStreamWriter(socket.getOutputStream())),true);
//
//			BufferedReader stdIn =
//					new BufferedReader(new InputStreamReader(System.in));
//
//			String linea;
//
//			try {
//				while (true) {
//					// Leo la entrada del usuario
//					linea = stdIn.readLine();
//					// La envia al servidor
//					salida.println(linea);
//					// Envía a la salida estándar la respuesta del servidor
//					linea = entrada.readLine();
//					System.out.println("Respuesta servidor: " + linea);
//					// Si es "Adios" es que finaliza la comunicación
//					if (linea.equals("Adios")) break;
//				}
//			} catch (IOException e) {
//				System.out.println("IOException: " + e.getMessage());
//			}
//			salida.close();
//			entrada.close();
//			stdIn.close();
//			
			
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			osra.closeTCPSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
