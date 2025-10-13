import java.util.*;
import java.math.*;

public class ecc{

    public static int modinv(int a, int p){
        return BigInteger.valueOf(a).modInverse(BigInteger.valueOf(p)).intValue();
    }

    public static boolean isvalidpoint(int x,int y, int a, int b,int p){
        // Ensure that inputs are positive modulo p for the calculation
        x = (x % p + p) % p;
        y = (y % p + p) % p;

        int left = (y*y)%p;
        
        // (x^3 + a*x + b) mod p
        int x_cubed = BigInteger.valueOf(x).multiply(BigInteger.valueOf(x)).multiply(BigInteger.valueOf(x)).mod(BigInteger.valueOf(p)).intValue();
        int ax = (a * x) % p;
        int right = (x_cubed + ax + b) % p;

        if((left + p) % p == (right + p) % p) return true;
        else return false;
    }

    public static int[] pointadd(int[] P, int[] Q, int a, int p){

        if(P==null) return Q;
        if(Q==null) return P;

        int x1 = P[0],y1 = P[1];
        int x2 = Q[0], y2 = Q[1];

        // Ensure positive coordinates for calculations
        x1 = (x1 % p + p) % p;
        y1 = (y1 % p + p) % p;
        x2 = (x2 % p + p) % p;
        y2 = (y2 % p + p) % p;

        // Check for P + (-P) = O (Point at Infinity)
        if (x1 == x2 && (y1 + y2) % p == 0) {
            return null; // Return point at infinity (O)
        }

        int l; // lambda (slope)

        if(x1==x2 && y1==y2){ // Point Doubling (P = Q)
            // lambda = (3*x1^2 + a) * (2*y1)^-1 mod p
            
            // Numerator: 3*x1^2 + a
            int x1_sq = BigInteger.valueOf(x1).multiply(BigInteger.valueOf(x1)).mod(BigInteger.valueOf(p)).intValue();
            int num = (3*x1_sq + a) % p;
            
            // Denominator: 2*y1
            int den = (2*y1) % p;

            if (den == 0) return null; // Tangent is vertical (Point at Infinity)

            l = (num * modinv(den,p))%p;
        }
        else{ // Point Addition (P != Q)
            // lambda = (y2 - y1) * (x2 - x1)^-1 mod p
            
            // Numerator: y2 - y1
            int num = (y2 - y1) % p;
            
            // Denominator: x2 - x1
            int den = (x2 - x1) % p;

            if (den == 0) return null; // Vertical line (P + (-P) = O)

            l = (num * modinv(den,p))%p;
        }

        // Ensure lambda is positive
        l = (l % p + p) % p;

        // x3 = lambda^2 - x1 - x2 mod p
        int l_sq = BigInteger.valueOf(l).multiply(BigInteger.valueOf(l)).mod(BigInteger.valueOf(p)).intValue();
        int x3 = (l_sq - x1 - x2) % p;

        // y3 = lambda * (x1 - x3) - y1 mod p
        int y3 = (l * (x1 - x3) - y1) % p;

        // Return point (x3, y3), ensuring coordinates are positive modulo p
        return new int[]{(x3 % p + p) % p,(y3 % p + p) % p};
    }


    // CORRECTED: Implements the efficient Double-and-Add algorithm
    public static int[] scalmul(int k, int[] P, int a, int p){
        if (k <= 0) return null; 

        int[] result = null; // Represents the point at infinity (O)
        int[] current = P; 

        while(k > 0){
            if((k & 1) == 1){
                result = pointadd(result, current, a, p); // If bit is 1, ADD
            }
            
            current = pointadd(current, current, a, p); // Always DOUBLE
            k >>= 1;
        }

        return result;
    }
}