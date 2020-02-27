import java.math.BigInteger;
import java.math.*;

class euclid {

    int inv(int modulo, int val) {
        BigInteger a = new BigInteger(String.valueOf(modulo));
        BigInteger m = new BigInteger(String.valueOf(val));
        BigInteger mod = a;
        BigInteger a1 = new BigInteger("0");
        BigInteger a2 = new BigInteger("1");
        BigInteger q = new BigInteger("0");
        BigInteger rem = new BigInteger("0");

        while (a.compareTo(new BigInteger("1")) > 0) {

            q = a.divide(m);
            rem = a.subtract(q.multiply(m));
            a = m;
            m = rem;

            if (m.compareTo(new BigInteger("0")) == 0 && a.compareTo(new BigInteger("1")) != 0) {
                System.out.println("Inverse not exist");
                System.exit(0);
                return -1;
            }
            rem = a1.subtract(a2.multiply(q));
            a1 = a2;
            a2 = rem;
        }
        if (a1.compareTo(new BigInteger("0")) < 0)
            a1 = a1.add(mod);
        return a1.intValue();
    }
}

public class QuadraticCongruenceModuloComposite {
    static int[] valOfX = new int[4];
    static int index = 0;

    public static void main(String args[]) {
        BigInteger a = new BigInteger(args[0]);
        BigInteger mod = new BigInteger(args[1]);
        System.out.println("a: " + a + " mod: " + mod + "\n");
        BigInteger[] res = new BigInteger[2];
        BigInteger[] res1 = new BigInteger[2];
        BigInteger[] res2 = new BigInteger[2];
        System.out.println("x^2 = " + a + "(mod " + mod + ")");
        res = fermat(mod);
        BigInteger fact1 = res[0];
        BigInteger fact2 = res[1];
        System.out.println("x^2 = " + res[0] + "(mod " + fact1 + ")");
        System.out.println("x^2 = " + res[1] + "(mod " + fact2 + ")\n");
        System.out.println("p*q: " + mod + " = p: " + fact1 + " * q: " + fact2);

        res1 = helper(a, fact1);
        res2 = helper(a, fact2);

        if (res1[0] == null || res2[0] == null || res1[1] == null || res2[1] == null) {
            return;
        }

        System.out.println("\nx = " + res1[0] + "(mod " + fact1 + ")\n" + "x = " + res1[1] + " (mod " + fact1 + ")\n"
                + "x = " + res2[0] + "(mod " + fact2 + ")\nx = " + res2[1] + "(mod " + fact2 + ")\n");

        int f1 = fact1.intValue();
        int f2 = fact2.intValue();

        int[] modular1 = new int[] { f2, f1 };
        int[] remainder1 = new int[] { res2[0].intValue(), res1[0].intValue() };

        ans(modular1, remainder1);

        int[] modular2 = new int[] { f2, f1 };
        int[] remainder2 = new int[] { res2[0].intValue(), res1[1].intValue() };

        ans(modular2, remainder2);

        int[] modular3 = new int[] { f2, f1 };
        int[] remainder3 = new int[] { res2[1].intValue(), res1[0].intValue() };

        ans(modular3, remainder3);

        int[] modular4 = new int[] { f2, f1 };
        int[] remainder4 = new int[] { res2[1].intValue(), res1[1].intValue() };

        ans(modular4, remainder4);
        System.out.print("x: ");
        for (int i = 0; i < 4; i++) {
            System.out.print(valOfX[i] + " ");
        }

    }

    public static void ans(int[] mod, int[] rem) {
        int n = 2;

        int ans = 0, sum = 0;
        int modulo = 1;

        euclid ec = new euclid();
        for (int i = 0; i < n; i++)
            modulo = modulo * mod[i];

        for (int i = 0; i < n; i++) {
            int m = modulo / mod[i];
            int ii = ec.inv(mod[i], m);
            ans += (rem[i] * m * ii);
        }
        while (ans < 0)
            ans += modulo;

        System.out.println("x = " + rem[0] + "(mod " + mod[0] + ")");
        System.out.println("x = " + rem[1] + "(mod " + mod[1] + ")");

        System.out.println("x : " + (ans) % modulo);
        valOfX[index] = ans % modulo;
        index++;

        System.out.println();

    }

    public static BigInteger[] helper(BigInteger a, BigInteger mod) {
        BigInteger[] res = new BigInteger[2];
        BigInteger x = BigInteger.valueOf(0);
        BigInteger zero = BigInteger.valueOf(0);
        BigInteger one = BigInteger.valueOf(1);
        BigInteger two = BigInteger.valueOf(2);
        while (zero.compareTo(a) == 1)
            a = a.add(mod);
        a = a.mod(mod);
        BigInteger mod2 = mod.subtract(one);
        mod2 = mod2.divide(two);

        if ((a.modPow(mod2, mod)).equals(one) == false) {
            System.out.println("Solutions does not exist");
            return res;
        }

        while (!isPerfectSquare(a)) {
            a = a.add(mod);
        }
        if (isPerfectSquare(a))
            x = a.sqrt();

        res[0] = x;
        res[1] = mod.subtract(x);

        return res;

    }

    public static BigInteger[] fermat(BigInteger num) {
        BigInteger[] res = new BigInteger[2];
        BigInteger a = num.sqrt();
        BigInteger temp = (a.pow(2));
        if (temp.compareTo(num) < 0) {
            a = a.add(BigInteger.ONE);
        }

        BigInteger b2 = (a.pow(2)).subtract(num);

        while (!isPerfectSquare(b2)) {
            a = a.add(BigInteger.ONE);
            b2 = (a.pow(2)).subtract(num);
        }

        BigInteger c = a.add(b2.sqrt());
        BigInteger d = a.subtract(b2.sqrt());

        res[0] = c;
        res[1] = d;

        return res;
    }

    static boolean isPerfectSquare(BigInteger x) {

        BigInteger sm = x.sqrt();
        BigInteger tmp;
        tmp = sm;
        tmp = tmp.multiply(sm);
        if (x.compareTo(tmp) == 0) {
            return true;
        }
        return false;
    }
}
