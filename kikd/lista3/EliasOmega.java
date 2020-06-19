package kikd.lista3;

import java.util.Iterator;
import java.util.LinkedList;

public class EliasOmega implements CodingType {
    @Override
    public String encode(int number) {
        number++;
        LinkedList<String> stringList = new LinkedList<>();
        stringList.add("0");

        int k = number;
        while (k > 1) {
            String binary = Integer.toBinaryString(k);
            stringList.add(binary);
            k = binary.length() - 1;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<String> reversedIterator = stringList.descendingIterator();
        while (reversedIterator.hasNext()) {
            sb.append(reversedIterator.next());
        }
        return sb.toString();
    }

    @Override
    public IndexWithCodeLength decodeNextCode(String message, int startIdx) {
        int n = 1;
        int i = 0;
        String code = "";
        while (message.charAt(startIdx + i) != '0') { // może startIdx + i + 1
            code = message.substring(startIdx + i, startIdx + i + n + 1);
            i += n + 1;
            n = Integer.parseInt(code, 2);
        }
        return new IndexWithCodeLength(n - 1, i + 1);
    }
}
