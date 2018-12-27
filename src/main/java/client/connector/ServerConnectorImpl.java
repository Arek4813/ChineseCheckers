package client.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * It is class responsible for connection with server, making a socket and launching with host and port
 */
public class ServerConnectorImpl implements ServerConnector {

    private static ServerConnectorImpl INSTANCE;
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 5000;

    private  Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;

    public static ServerConnectorImpl getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ServerConnectorImpl();
        return INSTANCE;
    }

    private ServerConnectorImpl() {}

    /**
     * Making object of Socket class, which allows connection with server
     * Thanks to input and output reader we may communicate with server
     * @throws IOException
     */
    @Override
    public void connectWithServer() throws IOException {
        socket = new Socket(HOST, PORT);
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void closeConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    @Override
    public BufferedReader getReader() {
        return inputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return outputStream;
    }
}
