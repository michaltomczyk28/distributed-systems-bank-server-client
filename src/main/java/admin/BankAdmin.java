package admin;

import shared.communication.SocketCommunicationBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class BankAdmin {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";


    public static void main(String[] args) throws IOException {
        Socket serverSocket = null;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            serverSocket = new Socket("localhost", 8082);
        } catch (Exception e) {
            System.out.println("Could not establish a connection with server.");
            System.exit(-1);
        }

        SocketCommunicationBus communicationBus = new SocketCommunicationBus(serverSocket);
        communicationBus.registerListener(input -> System.out.println(ANSI_RED + input + ANSI_RESET));

        while(true) {
            if(consoleReader.ready()) {
                String line = consoleReader.readLine();
                if (line != null) {
                    communicationBus.sendMessage(line);
                }
            }

            communicationBus.handleIncomingInput();
        }
    }
}
