import java.math.BigInteger;
import java.math.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Fermat {

  public static void trialDivision(BigInteger N) {
    assert (N.compareTo(BigInteger.ONE) == 1);
    assert (N.mod(new BigInteger("2")).equals(BigInteger.ZERO));
    BigInteger a = new BigInteger("3");
    while (a.compareTo(N) == -1) {
      if (N.mod(a) == BigInteger.ZERO) {
        break;
      } else {
        a = a.add(BigInteger.ONE);
      }
    }
    BigInteger b = N.divide(a);
    BigInteger p = a;
    System.out.println("Prime Factors are: " + p + "," + b);
  }

  static boolean checkPrime(BigInteger a) {
    return a.isProbablePrime(1);
  }

  public static void main(String[] args) {
    if (args.length == 1) {
      long start, end, time, milli = 1000000, sec = 1000000000;

      BigInteger input = new BigInteger(args[0]);
      System.out.println("Input number: " + input + " with the length: " + input.toString().length() + "\n");

      System.out.println("Fermat's Factorization: ");
      start = System.nanoTime();
      fermatFactors(input);
      end = System.nanoTime();
      time = end - start;
      System.out.println("time Taken: " + (double) time + " Nano sec");
      System.out.println("time Taken: " + (double) time / milli + " Milli ec");
      System.out.println("time Taken: " + (double) time / sec + " sec\n");

      System.out.println("Trial and Division: ");
      start = System.nanoTime();
      trialDivision(input);
      end = System.nanoTime();
      time = end - start;
      System.out.println("time Taken: " + (double) time + " Nano sec");
      System.out.println("time Taken: " + (double) time / milli + " Milli sec");
      System.out.println("time Taken: " + (double) time / sec + " sec");
    } else
      System.out.println("Correct way is: java Fermat <a composite or prime Number>");
  }

  public static void fermatFactors(BigInteger num) {
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

    System.out.println("Prime Factors are: " + d + "," + c);
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
