package kikd.lista3;

public class EliasDelta implements CodingType {
    @Override
    public String encode(int number) {
        number++;
        EliasGamma gammaCoding = new EliasGamma();
        String binary = Integer.toBinaryString(number);
        int n = binary.length();
        String nCode = gammaCoding.encode(n);
        binary = binary.substring(1);
        return nCode + binary;
    }

    @Override
    public IndexWithCodeLength decodeNextCode(String message, int startIdx) {
        EliasGamma gammaCoding = new EliasGamma();
        IndexWithCodeLength nWithLength = gammaCoding.decodeNextCode(message, startIdx);
        int endIndex = startIdx + nWithLength.indexCodeLength + nWithLength.encodedIndex - 1;
        String code = message.substring(startIdx + nWithLength.indexCodeLength, endIndex);
        String binary = '1' + code;
        return new IndexWithCodeLength(Integer.parseInt(binary, 2) - 1, endIndex - startIdx);
    }
}
