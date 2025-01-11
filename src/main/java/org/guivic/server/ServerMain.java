package org.guivic.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerMain {
    public static void main(String[] args) {

        try {
            DatagramSocket datagramSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            InetAddress address = InetAddress.getByName("localhost");

            String message = "";
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                datagramSocket.receive(datagramPacket);

                message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

                String[] input = message.split(":");
                int result = 0;
                String opr = input[0];
                int v1 = Integer.parseInt(input[1]);
                int v2 = Integer.parseInt(input[2]);
                if (opr.equals("suma")) {
                    result = v1 + v2;
                } else if (opr.equals("resta")) {
                    result = v1 - v2;
                }

                byte[] sendData = (result + "").getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        datagramPacket.getAddress(), datagramPacket.getPort());
                datagramSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}