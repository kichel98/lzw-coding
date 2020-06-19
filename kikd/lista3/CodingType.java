package kikd.lista3;

/**
 * Due to impossibility of '0' universal coding, we encode number incremented by one
 * and figure it in during decoding.
 */
public interface CodingType {
    String encode(int number);
    IndexWithCodeLength decodeNextCode(String message, int startIdx);
}
