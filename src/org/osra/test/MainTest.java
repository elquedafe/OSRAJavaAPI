package org.osra.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.osra.architecture.*;
import org.osra.*;


public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OSRA osra = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		//OSRA.register("alvaro","alvaro", "localhost");
		try {
			osra = new OSRA("alvaro", "alvaro", "localhost", "localhost");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			
			/*********POST METER***************/
			osra.createMeter("10.0.0.3", "", "", "", "tcp", 1000, 1000);
			
			/******POST FLOW*********/
//			osra.createFlows("10.0.0.1", "10.0.0.4", "80", "5000", "udp");
			
			/********GET METERS************/
//			List<Meter> meters = osra.getMeters();
			
			/********GET FLOWS*********/
//			List<Flow> flows = osra.getFlows();
			
			/********GET ENVIRONMENT*********/
//			Environment env = osra.getEnvironment();
			
			/*********SOCKETS WITH QoS***********/
//			Socket socket = osra.openTCPSocket("localhost", 8080, 100000, 1000000);
//			
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			osra.closeTCPSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
