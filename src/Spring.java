public class Spring {
    private double k = 1.0; // stiffness

    public Spring() {
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        double[] result = new double[(int) (t / dt) + 1];
        double x = x0;
        double v = v0;
        for (int i = 0; i < result.length; i++) {
            result[i] = x;
            double a = -omega * omega * x;
            x += v * dt;
            v += a * dt;
        }
        return result;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0.0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        double omega = Math.sqrt(k);
        double[] result = new double[(int) ((t1 - t0) / dt) + 1];
        double x = x0;
        double v = v0;
        for (int i = 0; i < result.length; i++) {
            result[i] = x;
            double a = -omega * omega * x;
            x += v * dt;
            v += a * dt;
        }
        return result;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double omega = Math.sqrt(k / m);
        double[] result = new double[(int) ((t1 - t0) / dt) + 1];
        double x = x0;
        double v = v0;
        for (int i = 0; i < result.length; i++) {
            result[i] = x;
            double a = -omega * omega * x;
            x += v * dt;
            v += a * dt;
        }
        return result;
    }

    public Spring inSeries(Spring that) {
        double equivalentK = this.k + that.k;
        return new Spring(equivalentK);
    }

    public Spring inParallel(Spring that) {
        double equivalentK = 1.0 / ((1.0 / this.k) + (1.0 / that.k));
        return new Spring(equivalentK);
    }
}
