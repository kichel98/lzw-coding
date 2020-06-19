package kikd.lista3;/*
 * author: Piotr Andrzejewski
 */

import java.util.*;

public class LZW {

    public byte[] encode(byte[] input, CodingType codingType) {
        // string is byte[] representation
        Map<String, Integer> dictionary = initializeEncodeDictionary();
        StringBuilder output = new StringBuilder();

        List<Byte> c = new ArrayList<>();
        c.add(input[0]);

        for (int i = 1; i < input.length; i++) {
            byte s = input[i];
            boolean isLongest = !extendSequenceIfDictionaryContains(dictionary, c, s);
            if (isLongest) {
                String sequenceCode = codingType.encode(getDictionaryIndexForSequence(dictionary, c));
                output.append(sequenceCode);

                Byte[] newSequence = c.toArray(new Byte[c.size() + 1]);
                newSequence[newSequence.length - 1] = s;

                dictionary.put(ByteStringUtils.byteArrayToString(newSequence), dictionary.size());

                c.clear();
                c.add(s);
            }
        }
        String sequenceCode = codingType.encode(getDictionaryIndexForSequence(dictionary, c));
        output.append(sequenceCode);

        ByteStringUtils.padStringWithZerosToFullBytes(output);
        return ByteStringUtils.getBytesByString(output);
    }

    public byte[] decode(byte[] input, CodingType codingType) {
        List<Byte[]> dictionary = initializeDecodeDictionary();
        List<Byte> output = new ArrayList<>();
        String inputBinaryString = ByteStringUtils.getBinaryStringFromBytes(input);

        int currentBitIdx = 0;
        IndexWithCodeLength pk = codingType.decodeNextCode(inputBinaryString, currentBitIdx);
        currentBitIdx += pk.indexCodeLength;
        output.addAll(Arrays.asList(dictionary.get(pk.encodedIndex)));

        while (currentBitIdx < inputBinaryString.length()) {
            IndexWithCodeLength k = codingType.decodeNextCode(inputBinaryString, currentBitIdx);
            currentBitIdx += k.indexCodeLength;
            Byte[] pk_sym = dictionary.get(pk.encodedIndex);
            Byte[] pc = new Byte[pk_sym.length + 1];
            System.arraycopy(pk_sym, 0, pc, 0, pk_sym.length);
            if (k.encodedIndex < dictionary.size()) {
                pc[pc.length - 1] = dictionary.get(k.encodedIndex)[0];
                output.addAll(Arrays.asList(dictionary.get(k.encodedIndex)));
            } else {
                pc[pc.length - 1] = pc[0];
                output.addAll(Arrays.asList(pc));
            }

            dictionary.add(pc);

            pk = k;
        }
        return ByteStringUtils.byteListToPrimitiveByteArray(output);
    }

    private Map<String, Integer> initializeEncodeDictionary() {
        Map<String, Integer> dictionary = new HashMap<>();
        byte minByteValue = -128;
        byte maxByteValue = 127;
        for (int i = minByteValue; i <= maxByteValue; i++) {
            dictionary.put(ByteStringUtils.byteArrayToString(new Byte[]{(byte) i}), i + 128);
        }
        return dictionary;
    }

    /*
        Initialize dictionary with all possible single byte (8-bit) values
     */
    private List<Byte[]> initializeDecodeDictionary() {
        List<Byte[]> dictionary = new ArrayList<>();
        byte minByteValue = -128;
        byte maxByteValue = 127;
        for (int i = minByteValue; i <= maxByteValue; i++) {
            dictionary.add(new Byte[]{(byte) i});
        }
        return dictionary;
    }

    /*
        if (c + s) is in dictionary, c = c + s
     */
    private boolean extendSequenceIfDictionaryContains(Map<String, Integer> dictionary, List<Byte> c, byte s) {
        c.add(s);
        if (!dictionaryContains(dictionary, c)) {
            c.remove(c.size() - 1);
            return false;
        }
        return true;

    }

    private boolean dictionaryContains(Map<String, Integer> dictionary, List<Byte> c) {
        return dictionary.containsKey(ByteStringUtils.byteArrayToString(ByteStringUtils.byteListToByteArray(c)));
    }

    private int getDictionaryIndexForSequence(Map<String, Integer> dictionary, List<Byte> c) {
        return dictionary.get(ByteStringUtils.byteArrayToString(ByteStringUtils.byteListToByteArray(c)));
    }
}
