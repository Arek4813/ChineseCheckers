/*package client;

import client.connector.ServerConnector;
import client.connector.ServerConnectorImpl;
import org.junit.Before;
import org.junit.Test;
import server.Server;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class ServerConnectorTest {
    ServerConnector connector = ServerConnectorImpl.getInstance();

    @Before
    public void startServer() {
        Server.main(null);
    }

    @Test
    public void connectsWithServerCorrectly() throws IOException {
        connector.connectWithServer();
        connector.closeConnection();
    }

    @Test
    public void closesConnectionCorrectly() throws IOException {
        connector.closeConnection();
    }

    @Test
    public void readerIsInitializedAfterConnection() throws IOException {
        connector.connectWithServer();
        assertNotNull(connector.getReader());
    }

    @Test
    public void writerIsInitializedAfterConnection() throws IOException {
        connector.connectWithServer();
        assertNotNull(connector.getWriter());
    }

    @Test
    public void writerIsClosedAfterClosingConnection() throws IOException {
        connector.connectWithServer();
        connector.closeConnection();
        connector.getWriter().println("message");
    }

    @Test (expected = IOException.class)
    public void readerIsClosedAfterClosingConnection() throws IOException {
        connector.connectWithServer();
        connector.closeConnection();
        connector.getReader().readLine();
    }
}
*/