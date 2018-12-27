package client.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created methods which implementation is in ServerConnectorImpl class
 */
public interface ServerConnector {
    void connectWithServer() throws IOException;
    void closeConnection() throws IOException;
    PrintWriter getWriter();
    BufferedReader getReader();
}
