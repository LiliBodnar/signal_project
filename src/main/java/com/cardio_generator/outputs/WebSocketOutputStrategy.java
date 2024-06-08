package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * This class implements the {@link OutputStrategy} interface
 * and broadcasts patient's data to every connected WebSocket client.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    /**
     * Constructs a {@code WebSocketOutputStrategy} and starts the WebSocket server on the specified port.
     *
     * @param port The port where the server listens for incoming WebSocket connections.
     */
    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    /**
     * Outputs the specified data for a patient by broadcasting it to every connected WebSocket client.
     * The data is in String format, seperated by commas.
     *
     * @param patientId The ID of the patient. It should be a positive integer.
     * @param timestamp The time of the recording in milliseconds.
     * @param label Describes the type of data (e.g., "heart rate"). It should not be null or empty.
     * @param data The actual data to be output. It should not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid (e.g., null or empty data/label).
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        // Broadcast the message to all connected clients
        for (WebSocket conn : server.getConnections()) {
            conn.send(message);
        }
    }

    private static class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}
