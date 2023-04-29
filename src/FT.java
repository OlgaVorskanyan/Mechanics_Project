import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class FT {
    public static double[] fft(double[] x) {
        int n = x.length;
        double[] mag = new double[n/2 + 1];

        // Compute FFT
        double[] re = Arrays.copyOf(x, n);
        double[] im = new double[n];
        for (int i = 0; i < n; i++) {
            im[i] = 0;
        }
        fft(re, im);

        // Compute magnitudes
        mag[0] = re[0] / n;
        for (int i = 1; i <= n/2; i++) {
            mag[i] = 2 * Math.sqrt(re[i]*re[i] + im[i]*im[i]) / n;
        }
        return mag;
    }

    public static void fft(double[] re, double[] im) {
        int n = re.length;
        if (n == 1) {
            return;
        }
        double[] reEven = new double[n/2];
        double[] imEven = new double[n/2];
        double[] reOdd = new double[n/2];
        double[] imOdd = new double[n/2];
        for (int i = 0; i < n/2; i++) {
            reEven[i] = re[2*i];
            imEven[i] = im[2*i];
            reOdd[i] = re[2*i+1];
            imOdd[i] = im[2*i+1];
        }
        fft(reEven, imEven);
        fft(reOdd, imOdd);
        for (int i = 0; i < n/2; i++) {
            double angle = -2 * Math.PI * i / n;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double reTemp = cos * reOdd[i] - sin * imOdd[i];
            double imTemp = cos * imOdd[i] + sin * reOdd[i];
            re[i] = reEven[i] + reTemp;
            im[i] = imEven[i] + imTemp;
            re[i+n/2] = reEven[i] - reTemp;
            im[i+n/2] = imEven[i] - imTemp;
        }
    }
}

