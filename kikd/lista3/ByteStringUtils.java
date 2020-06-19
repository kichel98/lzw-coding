package kikd.lista3;/*
 * author: Piotr Andrzejewski
 */

import java.util.List;

public class ByteStringUtils {

    /*
       Copied from StackOverflow ;)
     */
    static byte[] getBytesByString(StringBuilder text) {
        int splitSize = 8;

        int index = 0;
        int position = 0;

        byte[] resultByteArray = new byte[text.length() / splitSize];

        while (index < text.length()) {
            String binaryStringChunk = text.substring(index, Math.min(index + splitSize, text.length()));
            int byteAsInt = Integer.parseInt(binaryStringChunk, 2);
            resultByteArray[position] = (byte) byteAsInt;
            index += splitSize;
            position++;
        }
        return resultByteArray;
    }

    static String getBinaryStringFromBytes(byte[] bytes) {
        StringBuilder message = new StringBuilder();
        byte infoByte = bytes[0];
        for (int i = 1; i < bytes.length - 1; i++) {
            String byteString = Integer.toBinaryString((bytes[i] & 0xFF) + 0x100).substring(1);
            message.append(byteString);
        }
        String lastByte = Integer.toBinaryString((bytes[bytes.length - 1] & 0xFF) + 0x100).substring(1);
        for (byte i = 0; i < infoByte; i++) {
            message.append(lastByte.charAt(i));
        }
        return message.toString();
    }

    /**
     * To write bits to file, count of bits needs to be multiple of 8.
     * To do that, we pad binary string, appending zeros.
     * In first byte, we add "info byte", which is binary count of added zeros.
     */
    static void padStringWithZerosToFullBytes(StringBuilder output) {
        int validBitsInLastByte = output.length() % 8;
        if (validBitsInLastByte == 0) validBitsInLastByte = 8;

        // infoByte contains info how many bits in last byte are valid (non-padded)
        String infoByte = String.format("%8s", Integer.toBinaryString(validBitsInLastByte))
                .replace(' ', '0');

        // add infoByte at front in optimal way
        output.reverse()
                .append(new StringBuilder(infoByte).reverse())
                .reverse();

        while (output.length() % 8 != 0) {
            output.append('0');
        }
    }

    public static byte[] byteListToPrimitiveByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
        for (int index = 0; index < byteList.size(); index++) {
            byteArray[index] = byteList.get(index);
        }
        return byteArray;
    }

    public static Byte[] byteListToByteArray(List<Byte> byteList) {

        Byte[] byteArray = new Byte[byteList.size()];
        for (int index = 0; index < byteList.size(); index++) {
            byteArray[index] = byteList.get(index);
        }
        return byteArray;
    }

    /**
     * Method needed to use Byte[] as key in Map.
     * Converts every byte to char and groups to one String.
     */
    public static String byteArrayToString(Byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte singleByte : bytes) {
            sb.append((char) singleByte);
        }
        return sb.toString();
    }

}
