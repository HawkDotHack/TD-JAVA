package TD2;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class InAndOutNetworking {


    public static void sendMsg(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("pls enter your message : ");
            String input = scanner.nextLine();
            byte [] buf = new byte[256];
            buf = input.getBytes();
            scanner.close();
            try {
                InetAddress senderAddress = InetAddress.getByName("127.0.0.1");
                int senderPort = 1789;
                
                DatagramPacket outPacket = new DatagramPacket(buf, buf.length, senderAddress, senderPort);
                DatagramSocket socket = new DatagramSocket(1500);
                socket.send(outPacket); 
            } catch(IOException e){
                e.printStackTrace();
            }

    }

    public static void rcvMsg(){
        try{
            DatagramSocket socket = new DatagramSocket(1789);
            boolean running = true;
            byte[] buf = new byte[256];
            while (running) {
                DatagramPacket inPacket  = new DatagramPacket(buf, buf.length);
                socket.receive(inPacket);
                String received = new String(inPacket.getData(), 0, inPacket.getLength());
                

                if (received.equals("end")) {
                    running = false;
                    continue;
                }else{
                    System.out.println("Greetings : " + received);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Thread send = new Thread(() -> sendMsg());
        Thread rcv = new Thread(()-> rcvMsg());
        send.start();
        rcv.start();
    }
}