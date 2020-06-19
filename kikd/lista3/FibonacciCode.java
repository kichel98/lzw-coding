package kikd.lista3;

import java.util.Iterator;
import java.util.LinkedList;

public class FibonacciCode implements CodingType {
    @Override
    public String encode(int number) {

        number++; // Handle zero case
        String fibCode = getFibonacciCode(number);
        return new StringBuilder(fibCode).reverse().append('1').toString();
    }

    @Override
    public IndexWithCodeLength decodeNextCode(String message, int startIdx) {
        int i = 0;
        int previousNumber = 1;
        int currentNumber = 1;
        int code = 0;
        while (!message.substring(startIdx + i, startIdx + i + 2).equals("11")) {
            if (message.charAt(startIdx + i) == '1') {
                code += currentNumber;
            }

            int tmp = currentNumber;
            currentNumber = previousNumber + currentNumber;
            previousNumber = tmp;

            i++;

        }
        code += currentNumber;
        i += 2;

        code--; // Handle zero case
        return new IndexWithCodeLength(code, i);
    }

    private static String getFibonacciCode(int number) {
        // handle 0!
        LinkedList<Integer> fibNumbers = new LinkedList<>();
        int previousNumber = 1;
        int currentNumber = 1;

        while (currentNumber <= number) {
            int tmp = currentNumber;
            currentNumber = previousNumber + currentNumber;
            previousNumber = tmp;
            fibNumbers.add(previousNumber);
        }

        Iterator<Integer> iterator = fibNumbers.descendingIterator();
        StringBuilder code = new StringBuilder();
        while (iterator.hasNext()) {
            int fibNumber = iterator.next();
            if (fibNumber <= number) {
                number -= fibNumber;
                code.append('1');
            } else {
                code.append('0');
            }
        }
        return code.toString();
    }
}
