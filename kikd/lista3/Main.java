package kikd.lista3;/*
 * author: Piotr Andrzejewski
 * Zadanie na 4 - LZC (kikd.lista3.LZW ze zwiększaną liczbą bitów na kod)
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {

    enum ProgramType {
        ENCODE, DECODE
    }

    public static void main(String[] args) {
        Path inputPath;
        Path outputPath;
        ProgramType programType;
        CodingType codingType;

        if (args.length != 3 && args.length != 4) {
            printUsage();
            return;
        }

        if (args[0].equals("--encode")) {
            programType = ProgramType.ENCODE;
        } else if (args[0].equals("--decode")) {
            programType = ProgramType.DECODE;
        } else {
            printUsage();
            return;
        }

        if (args.length == 3) {
            inputPath = Paths.get(args[1]);
            outputPath = Paths.get(args[2]);
            codingType = new EliasGamma();
        } else {
            inputPath = Paths.get(args[2]);
            outputPath = Paths.get(args[3]);
            switch (args[1]) {
                case "--gamma":
                    codingType = new EliasGamma();
                    break;
                case "--delta":
                    codingType = new EliasDelta();
                    break;
                case "--omega":
                    codingType = new EliasOmega();
                    break;
                case "--fib":
                    codingType = new FibonacciCode();
                    break;
                default:
                    printUsage();
                    return;
            }
        }

        LZW lzw = new LZW();
        try {
            byte[] input = Files.readAllBytes(inputPath);
            byte[] output;
            if (input.length == 0) {
                throw new IllegalArgumentException();
            }
            if (programType == ProgramType.ENCODE) {
                output = lzw.encode(input, codingType);
                prepareAndPrintStats(input, output);
            } else {
                output = lzw.decode(input, codingType);
            }
            Files.write(outputPath, output);
        } catch (IllegalArgumentException e) {
            System.out.println("Input file cannot be empty");
        } catch (IOException e) {
            System.out.println("Unable to read/write file");
        }
    }

    private static void prepareAndPrintStats(byte[] input, byte[] output) {
        Map<Byte, Integer> inputFrequencyMap = StatsUtils.getFrequencyMap(input);
        Map<Byte, Integer> outputFrequencyMap = StatsUtils.getFrequencyMap(output);
        System.out.println("Dlugosc kodowanego pliku: " + input.length);
        System.out.println("Dlugosc uzyskanego kodu: " + output.length);
        System.out.println("Stopien kompresji: " + (double) input.length / output.length);
        System.out.println("Entropia kodowanego tekstu: " + StatsUtils.calcEntropy(inputFrequencyMap, input.length));
        System.out.println("Entropia uzyskanego kodu: " + StatsUtils.calcEntropy(outputFrequencyMap, output.length));
    }

    private static void printUsage() {
        System.out.println("usage: --encode/--decode (--delta/--gamma/--omega/--fib) file.in file.out");
    }
}
