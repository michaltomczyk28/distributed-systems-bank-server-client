package shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketCommunicationBus {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<SocketCommunicationListener> listeners;

    public SocketCommunicationBus(Socket socket) throws IOException {
        this.socket = socket;
        this.listeners = new ArrayList<>();

        this.out = new PrintWriter(this.socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

    public void registerListener(SocketCommunicationListener listener) {
        this.listeners.add(listener);
    }

    public void handleIncomingInput() throws IOException {
        String inputLine = this.in.readLine();

        if(inputLine != null) {
            this.notifyAllListeners(inputLine);
        }
    }

    private void notifyAllListeners(String input) {
        this.listeners.forEach((SocketCommunicationListener listener) -> listener.onInput(input));
    }
}
