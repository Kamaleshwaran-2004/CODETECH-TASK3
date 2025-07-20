import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER, PORT);
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Receiving initial prompt
            System.out.println(in.readLine());
            out.println(keyboard.readLine());

            // Thread to receive messages from server
            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }).start();

            // Sending messages to server
            String message;
            while ((message = keyboard.readLine()) != null) {
                out.println(message);
                if (message.equalsIgnoreCase("exit")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
