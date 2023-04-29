import java.util.Arrays;

public class Converter8Bit extends Converter {
    private static final int NUM_BITS = 8;
    private static final double SPRING_MASS = 0.001;
    private static final double SPRING_K = 500.0;

    @Override
    public Spring[] bitsToSprings(boolean[] bits) {
        int numSprings = bits.length / NUM_BITS;
        Spring[] springs = new Spring[numSprings];
        for (int i = 0; i < numSprings; i++) {
            boolean[] subBits = Arrays.copyOfRange(bits, i * NUM_BITS, (i + 1) * NUM_BITS);
            double frequency = binaryToFrequency(subBits);
            springs[i] = new Spring(SPRING_MASS, SPRING_K);
        }
        return springs;
    }

    private double binaryToFrequency(boolean[] bits) {
        int decimalValue = 0;
        for (int i = 0; i < bits.length; i++) {
            decimalValue += bits[i] ? Math.pow(2, i) : 0;
        }
        return decimalValue * 10.0 + 100.0;
    }


    public static void main(String[] args) {
        Converter8Bit converter = new Converter8Bit();
        boolean[] bits1 = {true, true, false, false, true, false, true, false}; // 205 in decimal
        boolean[] bits2 = {false, false, true, true, false, true, false, true}; // 37 in decimal
        boolean[] bits3 = {true, false, true, false, true, false, true, false}; // 170 in decimal
        double value1 = converter.calculateDecimal(bits1);
        double value2 = converter.calculateDecimal(bits2);
        double value3 = converter.calculateDecimal(bits3);
        System.out.println(value1); // expected output: 205.0
        System.out.println(value2); // expected output: 37.0
        System.out.println(value3); // expected output: 170.0
    }

}
