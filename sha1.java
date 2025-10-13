public class sha1{

    public static int leftrot(int n,int b){
        return Integer.rotateLeft(n,b);
    }

    public static int func(int b, int c, int d){
        return (b & c) | (~b &d);
    }

    public static void main(String[] args){

        int a = 0x12345678;
        int b = 0x4567AB87;
        int c = 0xBC67AB87;
        int d = 0x4557ABE7;
        int e = 0xCB67EF87;

        int w0 = 0x61644023;
        int k = 0x41212A87;

        int temp = leftrot(a,5) + e + func(b,c,d) + w0 +k;
        temp = temp & 0xFFFFFFFF;

        e = d;
        d = c;
        c = leftrot(b,30);
        b = a;
        a = temp;

        System.out.printf("a - 0x%08X\n",a);
        System.out.printf("b - 0x%08X\n",b);
        System.out.printf("c - 0x%08X\n",c);
        System.out.printf("d - 0x%08X\n",d);
        System.out.printf("e - 0x%08X\n",e);

    }
}
