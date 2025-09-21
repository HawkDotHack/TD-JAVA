package TD2;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start(int port) {
         try {
            this.serverSocket = new ServerSocket(port);
            while (true)
                new EchoClientHandler(this.serverSocket.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void stop() {
        try{
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private List<Double> numbers = new ArrayList<>();

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        static void writeDoubleToSocket(Socket socket, double number) throws IOException {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeDouble(number);
        }

        static double readDoubleFromSocket(Socket socket) throws IOException {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            return in.readDouble();
        }
        public void run() {
            try{
                System.out.println("RUNNING START");
                
                while (true) {
                        this.numbers.add(readDoubleFromSocket(this.clientSocket));
                        double avg = this.numbers.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                        System.out.println(this.numbers);
                        System.out.println(avg);
                        writeDoubleToSocket(this.clientSocket, avg);
                    }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        EchoMultiServer serv = new EchoMultiServer();
        serv.start(1789);
    }
}