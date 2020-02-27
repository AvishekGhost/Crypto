package com.avishek;

public class ExtendedEuclidean {
    public static int extendedEuclidean(int a, int b, int x, int y) {
        if (a == 0) {
            x = 0;
            y = 1;
            return b;
        }
        int x1 = 1, y1 = 1;
        int gcd = extendedEuclidean(b % a, a, x1, y1);

        x = y1 - (b / a) * x1;
        y = x1;

        return gcd;
    }

    public static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println( input + " is not a valid integer");
            return false;
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            if (isNumber(args[0]) && isNumber(args[1])) {
                int a = Integer.parseInt(args[0]), b = Integer.parseInt(args[1]);
                int g = extendedEuclidean(a, b, 1, 1);
                System.out.print("Extended Euclidean GCD: " + g);
            } else {
                System.out.println("Try: 'ExtendedEuclidean <number> <number>'");
            }
        } else {
            System.out.println("Try: 'ExtendedEuclidean <number> <number>'");
        }
    }
}

