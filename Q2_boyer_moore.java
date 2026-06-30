import java.util.*;

public class Q2_boyer_moore {

    static int[] computePrefix(String p) {

        int m = p.length();
        int[] pi = new int[m + 1];

        int k = 0;

        for (int q = 2; q <= m; q++) {

            while (k > 0 && p.charAt(k) != p.charAt(q - 1))
                k = pi[k];

            if (p.charAt(k) == p.charAt(q - 1))
                k++;

            pi[q] = k;
        }

        return pi;
    }

    static int[] computeGoodSuffix(String pattern) {

        int m = pattern.length();

        int[] gamma = new int[m + 1];

        int[] pi = computePrefix(pattern);

        int[] piRev =
                computePrefix(new StringBuilder(pattern).reverse().toString());

        for (int j = 0; j <= m; j++)
            gamma[j] = m - pi[m];

        for (int l = 1; l <= m; l++) {

            int j = m - piRev[l];

            if (gamma[j] > l - piRev[l])
                gamma[j] = l - piRev[l];
        }

        return gamma;
    }

    static int[] badCharShift(String pattern, char badChar, int j) {

        for (int k = j - 1; k >= 0; k--) {

            if (pattern.charAt(k) == badChar) {

                int display = j - k - 1;

                int actual = j - k;

                return new int[]{display, actual};
            }
        }

        int value = j + 1;

        return new int[]{value, value};
    }
    static void boyerMooreMatcher(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int[] gamma = computeGoodSuffix(pattern);

        System.out.println("Text    : " + text);
        System.out.println("Pattern : " + pattern);
        System.out.println("------------------------------------------------------------");

        int s = 0;
        int step = 1;
        boolean found = false;

        ArrayList<Integer> positions = new ArrayList<>();

        while (s <= n - m) {

            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j--;

            String pAligned = " ".repeat(s) + pattern;

            System.out.println("\nStep " + step++);
            System.out.println("Text    : " + text);
            System.out.println("Pattern : " + pAligned);

            if (j < 0) {

                found = true;
                positions.add(s);

                System.out.println("Match Found at Index : " + s);

                int move = gamma[0];
                if (move < 1)
                    move = 1;

                System.out.println("Good Suffix Shift : " + move);
                System.out.println("Move Pattern By   : " + move);

                // Continue searching for next occurrence
                s = s + 1;

                System.out.println("------------------------------------------------------------");
                continue;
            }

            char mismatch = text.charAt(s + j);

            int[] bc = badCharShift(pattern, mismatch, j);

            int bcShift = bc[1];

            int gsShift = (j < m - 1) ? gamma[j + 1] : 1;

            int finalShift = Math.max(bcShift, gsShift);

            if (finalShift < 1)
                finalShift = 1;

            System.out.println("Mismatch at Text[" + (s + j) + "] = '" +
                    mismatch + "', Pattern[" + j + "] = '" +
                    pattern.charAt(j) + "'");

            System.out.println("Bad Character Shift : " + bcShift);
            System.out.println("Good Suffix Shift   : " + gsShift);
            System.out.println("Move Pattern By     : " + finalShift);

            System.out.println("------------------------------------------------------------");

            s += finalShift;
        }

        System.out.println("\n================ FINAL RESULT ================");

        if (!found) {
            System.out.println("Pattern not found.");
        } else {

            for (int i = 0; i < positions.size(); i++) {
                System.out.println((i + 1) + ". Match found at index " + positions.get(i));
            }
        }
    }

    public static void main(String[] args) {

        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        boyerMooreMatcher(text, pattern);
    }
}