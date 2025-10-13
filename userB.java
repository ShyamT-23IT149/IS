import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.util.Scanner;

public class UserB {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Input prime and generator
        System.out.print("Enter prime number (p): ");
        BigInteger p = new BigInteger(sc.nextLine());
        System.out.print("Enter primitive root (g): ");
        BigInteger g = new BigInteger(sc.nextLine());

        // Input private key
        System.out.print("Enter UserB private key (b): ");
        int b = Integer.parseInt(sc.nextLine());

        // Compute public key
        BigInteger B_pub = g.modPow(BigInteger.valueOf(b), p);
        System.out.println("UserB public key: " + B_pub);

        // Connect to UserA
        Socket socket = new Socket("localhost", 5000);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Receive UserA public key
        BigInteger A_pub = new BigInteger(in.readUTF());
        System.out.println("UserA public key: " + A_pub);

        // Send UserB public key
        out.writeUTF(B_pub.toString());

        // Compute shared secret
        BigInteger sharedSecret = A_pub.modPow(BigInteger.valueOf(b), p);
        System.out.println("Shared secret: " + sharedSecret);

        socket.close();
        sc.close();
    }
}
