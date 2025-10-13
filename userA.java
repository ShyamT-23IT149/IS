import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.util.Scanner;

public class UserA {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Input prime and generator
        System.out.print("Enter prime number (p): ");
        BigInteger p = new BigInteger(sc.nextLine());
        System.out.print("Enter primitive root (g): ");
        BigInteger g = new BigInteger(sc.nextLine());

        // Input private key
        System.out.print("Enter UserA private key (a): ");
        int a = Integer.parseInt(sc.nextLine());

        // Compute public key
        BigInteger A_pub = g.modPow(BigInteger.valueOf(a), p);
        System.out.println("UserA public key: " + A_pub);

        // Start server
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Waiting for UserB...");
        Socket socket = serverSocket.accept();
        System.out.println("UserB connected!");

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Send public key
        out.writeUTF(A_pub.toString());

        // Receive UserB public key
        BigInteger B_pub = new BigInteger(in.readUTF());
        System.out.println("UserB public key: " + B_pub);

        // Compute shared secret
        BigInteger sharedSecret = B_pub.modPow(BigInteger.valueOf(a), p);
        System.out.println("Shared secret: " + sharedSecret);

        socket.close();
        serverSocket.close();
        sc.close();
    }
}
