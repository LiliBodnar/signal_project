package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * This class implements the {@link OutputStrategy} interface
 * and sends patient's data over a TCP connection.
 * The server listens for a connection on the specified port and sends data to the connected client.
 */
public class TcpOutputStrategy implements OutputStrategy {

    /** The server socket which listens for incoming connections. */
    private ServerSocket serverSocket;

    /** The client socket which represents the connection to the client. */
    private Socket clientSocket;

    /** The output stream for sending data to the client. */
    private PrintWriter out;

    /**
     * Constructs a {@code TcpOutputStrategy} and starts the TCP server on the specified port.
     * The server accepts a client connection in a separate thread.
     *
     * @param port The port where the server listens for incoming connections.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public TcpOutputStrategy(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Outputs the specified data for a patient by sending it over the TCP connection to the connected client.
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
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
