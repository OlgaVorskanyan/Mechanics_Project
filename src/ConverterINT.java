import java.util.ArrayList;

public class ConverterInt extends Converter {

    private Spring equivalentSpring;

    @Override
    public Spring[] bitsToSprings(boolean[] bits) {
        // Find the highest non-zero bit
        int maxBit = bits.length - 1;
        while (maxBit >= 0 && !bits[maxBit]) {
            maxBit--;
        }

        // Create the unit springs
        ArrayList<Spring> springs = new ArrayList<>();
        for (int i = 0; i <= maxBit; i++) {
            double mass = Math.pow(2, i);
            double stiffness = 1.0;
            Spring spring = new Spring(mass, stiffness);
            springs.add(spring);
        }

        // Connect the springs in series to form the equivalent spring
        equivalentSpring = springs.get(0);
        for (int i = 1; i < springs.size(); i++) {
            equivalentSpring = equivalentSpring.inSeries(springs.get(i));
        }

        return springs.toArray(new Spring[0]);
    }

    public double calculateDecimalValue(boolean[] bits) {
        // Calculate the amplitude of the equivalent spring
        double amplitude = calculateAmplitude(equivalentSpring);

        // Calculate the frequency of the equivalent spring
        double frequency = equivalentSpring.getFrequency();

        // Calculate the power spectrum of the bit sequence
        int n = bits.length;
        double[] real = new double[n];
        double[] imag = new double[n];
        for (int i = 0; i < n; i++) {
            real[i] = bits[i] ? 1 : -1;
        }
        FT.fft(real, imag);
        double[] powerSpectrum = new double[n];
        for (int i = 0; i < n; i++) {
            powerSpectrum[i] = Math.sqrt(real[i] * real[i] + imag[i] * imag[i]);
        }

        // Calculate the decimal value
        double decimalValue = 0.0;
        double deltaF = frequency / n;
        for (int i = 0; i < n / 2; i++) {
            decimalValue += powerSpectrum[i] * amplitude * deltaF;
        }
        return decimalValue;
    }
}
