package TD2;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Networking {

    static void writeDoubleToSocket(Socket socket, double number) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeDouble(number);
    }

    static double readDoubleFromSocket(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readDouble();
    }

    static int readIntFromConsole() throws IOException {
        // System.in is an InputStream which we wrap into the more capable BufferedReader
        System.out.println("Please enter the next number.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String input = reader.readLine();
            try {
                int n = Integer.parseInt(input);
                return n;
            } catch (NumberFormatException e) {
                System.out.print("Not an integer, try again: ");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the averaging client.");
        Socket serverSocket = new Socket("127.0.0.1", 1789);
        boolean running = true;
        while (running) {
            int numberToSend = readIntFromConsole();
            writeDoubleToSocket(serverSocket, numberToSend);
            System.out.println("Sending to server awaiting response...");
            Double avg = readDoubleFromSocket(serverSocket);
            System.out.println("Received current average: " + avg + " (computed on our HPC cluster)");
        }
        serverSocket.close();
    }
}