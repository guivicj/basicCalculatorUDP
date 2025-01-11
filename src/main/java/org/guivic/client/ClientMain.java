package org.guivic.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();

            InetAddress address = InetAddress.getByName("localhost");
            int serverPort = 9876;

            System.out.print("Introduce una Operación: Suma - Resta - EXIT: ");
            String opr = new Scanner(System.in).nextLine().toLowerCase();

            if (opr.equals("exit")) {
                System.out.println("Saliendo del programa");
            } else {
                System.out.print("Introduce el primer número: ");
                int v1 = new Scanner(System.in).nextInt();

                System.out.print("Introduce el segundo número: ");
                int v2 = new Scanner(System.in).nextInt();

                String mensaje = String.format("%s:%d:%d", opr, v1, v2);
                byte[] sendData = mensaje.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, serverPort);
                datagramSocket.send(sendPacket);


                DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                datagramSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Respuesta del Servidor: " + message);

                datagramSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
