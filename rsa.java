import java.util.*;
import java.math.*;

public class rsa{

    public static boolean isprime(BigInteger p){
        return p.isProbablePrime(10);
    }

    public static BigInteger[] key(BigInteger p, BigInteger q){
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        BigInteger d= e.modInverse(phi);

        return new BigInteger[]{e,d};
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        BigInteger p=null, q =null,n = null;

        System.out.print("Enter the prime p ");
        p = new BigInteger(sc.next());
        
        System.out.print("Enter the prime q ");
        q = new BigInteger(sc.next());

        if(!isprime(p) || !isprime(q)) return;
        n = p.multiply(q);

        BigInteger[] keys = key(p,q);

        BigInteger e = keys[0];
        BigInteger d = keys[1];

        char m = 'a';
        BigInteger mes = BigInteger.valueOf((int)m);
        System.out.println("ENCRYPTION : ");
        BigInteger cipher = mes.modPow(e,n);
        System.out.println(cipher);


        System.out.println("DECRYPTION : ");
        char ans = (char)(cipher.modPow(d,n)).intValue();
        System.out.println(ans);
    }
}
