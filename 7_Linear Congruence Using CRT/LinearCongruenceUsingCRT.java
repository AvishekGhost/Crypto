import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

import java.util.Scanner;

//exapmle
/*
x = 2 mod 3
x = 3 mod 5
x = 2 mod 7

ans  x = 23 (mod 105)
When we divide 233 by 105, we get remainder 23.

 */
public class LinearCongruenceUsingCRT {
    static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b == BigInteger.ZERO)
            return a;

        return gcd(b, a .mod(b) );
    }


    static boolean checkSolutionPossibility(BigInteger[] b, int n) {
        for (int x = 0; x < n; x++) {
            for (int y = x + 1; y < n; y++) {
                if (!(b[x].gcd(b[y])).equals(BigInteger.ONE)) {
                    System.out.println(b[x].gcd(b[y]));
                    return true;
                }
            }
        }
        return false;
    }

    static BigInteger chineseRemainderTheorem(BigInteger[] a, BigInteger[] b, int n) {
       BigInteger[] Minv = new BigInteger[n];
        BigInteger q, r, r1, r2, t, t1, t2;

        BigInteger total = BigInteger.ONE;
        for (int k = 0; k < n; k++)
            total = total.multiply(b[k]);

        for (int k = 0; k < n; k++) {
            r1 = b[k];
            r2 = total.divide(b[k]) ;
            t1 = BigInteger.ZERO;
            t2 = BigInteger.ONE;

            while (r2.compareTo(BigInteger.ZERO) ==1 ) {
                q = r1.divide(r2);
                r = r1.subtract(q.multiply(r2));
                r1 = r2;
                r2 = r;

                t = t1.subtract(q.multiply(t2));
                t1 = t2;
                t2 = t;
            }

            if (r1.equals(BigInteger.ONE))
                Minv[k] = t1;

            if ((Minv[k].compareTo(BigInteger.ZERO)) == -1)
                Minv[k] = Minv[k].add(b[k]) ;
        }

        BigInteger x = BigInteger.ZERO;
        for (int k = 0; k < n; k++)
            x = x.add((a[k].multiply(total.multiply(Minv[k]) ) ).divide(b[k])) ;

        return x;
    }

    public static void main(String[] args) throws Exception {
        InputStreamReader r=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(r);

        System.out.print("number of eq: ");
        int n = Integer.parseInt(br.readLine());

        BigInteger[] a = new BigInteger[n];
        BigInteger[] b = new BigInteger[n];

        for (int i = 0; i < n; i++) {
            System.out.print("enter a and b for X = a(mod b): \na: ");
            a[i] = new BigInteger(br.readLine());
            System.out.print("b: ");
            b[i] = new BigInteger(br.readLine());
        }

        BigInteger div = BigInteger.ONE;

        for(BigInteger bigInteger: b){
            div = div.multiply(bigInteger);
        }
        System.out.println(div);
        BigInteger crt;
        if (!checkSolutionPossibility(b, n)) {
            crt = chineseRemainderTheorem(a, b, n);
            System.out.println("X = " + crt + " (mod " + div + ")");
            System.out.println("X = "+ crt.mod(div));
        }
        else
            System.out.print("The given equations has no solutions.\n");
    }
}
