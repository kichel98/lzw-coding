package kikd.lista3;

public class EliasGamma implements CodingType {
    @Override
    public String encode(int number) {
        number++;
        String binary = Integer.toBinaryString(number);
        String lengthPart = "0".repeat(binary.length() - 1);
        return lengthPart + binary;

    }

    @Override
    public IndexWithCodeLength decodeNextCode(String message, int startIdx) {
        int n = 0;
        int i = 0;
        while (message.charAt(startIdx + i) == '0') {
            n++;
            i++;
        }
        n++; // n-1 -> n
        int codeLength = i + n;

        String binary = message.substring(startIdx + i, startIdx + codeLength);
        return new IndexWithCodeLength(Integer.parseInt(binary, 2) - 1, codeLength);
    }
}
