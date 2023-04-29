public abstract class Converter {
    private double[] frequencies;
    private double[] amplitudes;

    public abstract Spring[] bitsToSprings(boolean[] bits);

    public double calculateDecimal(boolean[] bits) {
        Spring[] springs = bitsToSprings(bits);
        Spring equivalentSpring = equivalentSpring(springs);
        double frequency = equivalentSpring.getFrequency();
        double amplitude = calculateAmplitude(equivalentSpring);
        frequencies = new double[] { frequency };
        amplitudes = new double[] { amplitude };
        double[] real = new double[bits.length];
        double[] imag = new double[bits.length];
        for (int i = 0; i < bits.length; i++) {
            real[i] = bits[i] ? 1 : -1;
        }
        FT.fft(real, imag);
        double[] powerSpectrum = new double[bits.length];
        for (int i = 0; i < bits.length; i++) {
            powerSpectrum[i] = Math.sqrt(real[i] * real[i] + imag[i] * imag[i]);
        }
        return calculateDecimalValue(powerSpectrum);
    }

    private Spring equivalentSpring(Spring[] springs) {
        Spring equivalentSpring = springs[0];
        for (int i = 1; i < springs.length; i++) {
            equivalentSpring = equivalentSpring.inParallel(springs[i]);
        }
        return equivalentSpring;
    }

    double calculateAmplitude(Spring spring) {
        return spring.getMass() / spring.getStiffness();
    }

    private double calculateDecimalValue(double[] powerSpectrum) {
        double decimalValue = 0.0;
        double deltaF = frequencies[0] / powerSpectrum.length;
        for (int i = 0; i < powerSpectrum.length / 2; i++) {
            decimalValue += powerSpectrum[i] * amplitudes[0] * deltaF;
        }
        return decimalValue;
    }

    public static class Spring {
        private double stiffness;
        private double mass;

        public Spring(double stiffness, double mass) {
            this.stiffness = stiffness;
            this.mass = mass;
        }

        public double getStiffness() {
            return stiffness;
        }

        public double getMass() {
            return mass;
        }

        public double getFrequency() {
            return Math.sqrt(stiffness / mass);
        }

        public Spring inParallel(Spring spring) {
            double newStiffness = stiffness + spring.stiffness;
            double newMass = mass + spring.mass;
            return new Spring(newStiffness, newMass);
        }
    }
}


