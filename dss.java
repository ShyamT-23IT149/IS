import java.util.*;
import java.math.*;


public class dss{

    public static boolean isprime(BigInteger p){
        return p.isProbablePrime(10);
    }

    public static void main(String[] args){

        Scanner sc= new Scanner(System.in);
        
        System.out.print("Enter the prime p: ");
        BigInteger p = new BigInteger(sc.next());

        System.out.print("Enter the prime q: ");
        BigInteger q = new BigInteger(sc.next());


        if(!isprime(p) || !isprime(q)) return;

        if(!p.subtract(BigInteger.ONE).mod(q).equals(BigInteger.ZERO)) return;

        System.out.print("Enter the h:");
        BigInteger h = new BigInteger(sc.next());

        BigInteger g = h.modPow((p.subtract(BigInteger.ONE)).divide(q),p);

        System.out.print("Enter the pvt key x (between 1 and "+(q.subtract(BigInteger.ONE))+"): ");
        BigInteger x = new BigInteger(sc.next());

        BigInteger y = g.modPow(x,p);

        System.out.println("g - "+g+" x - "+x);



        System.out.println("SIGNATURE GENERATION !!");

        System.out.print("Enter the k (between 1 and "+(q.subtract(BigInteger.ONE))+"): ");
        BigInteger k = new BigInteger(sc.next());

        BigInteger r = g.modPow(k,p).mod(q);
        BigInteger kinv = k.modInverse(q);

        System.out.print("Enter the Hm: ");
        BigInteger hm = new BigInteger(sc.next());

        BigInteger s = kinv.multiply(hm.add(x.multiply(r))).mod(q);


        System.out.println("r - "+r+" s - "+s);


        System.out.println("\nSIGNATURE VERIFICATION");

        BigInteger w = s.modInverse(q);
        BigInteger u1 = (hm.multiply(w)).mod(q);
        BigInteger u2 = (r.multiply(w)).mod(q);

        BigInteger v = (g.modPow(u1,p).multiply(y.modPow(u2,p))).mod(p).mod(q);

        if(v.equals(r)) System.out.print("VERIFIED !!!");
        else System.out.println("NOT VERIFIED!!!");
    }
}
