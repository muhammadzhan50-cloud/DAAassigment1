import java.util.Arrays;
import java.util.Comparator;

public class Problem4 {

    public double minDistBrute(double[][] p) {
        if (p == null || p.length < 2) {
            return 0.0;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        int n = p.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = distance(p[i], p[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }

        return minDistance;
    }

    public double minDistSmart(double[][] p) {
        if (p == null || p.length < 2) {
            return 0.0;
        }

        double[][] points = p.clone();
        Arrays.sort(points, Comparator.comparingDouble(a -> a[0]));

        return closestPairRecursive(points, 0, points.length - 1);
    }

    private double closestPairRecursive(double[][] points, int low, int high) {
        int count = high - low + 1;
        if (count <= 3) {
            double minDist = Double.POSITIVE_INFINITY;
            for (int i = low; i <= high; i++) {
                for (int j = i + 1; j <= high; j++) {
                    minDist = Math.min(minDist, distance(points[i], points[j]));
                }
            }
            return minDist;
        }

        int mid = low + (high - low) / 2;
        double midX = points[mid][0];

        double dLeft = closestPairRecursive(points, low, mid);
        double dRight = closestPairRecursive(points, mid + 1, high);
        double d = Math.min(dLeft, dRight);

        double[][] strip = new double[count][2];
        int stripSize = 0;

        for (int i = low; i <= high; i++) {
            if (Math.abs(points[i][0] - midX) < d) {
                strip[stripSize++] = points[i];
            }
        }

        Arrays.sort(strip, 0, stripSize, Comparator.comparingDouble(a -> a[1]));

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j][1] - strip[i][1]) < d; j++) {
                d = Math.min(d, distance(strip[i], strip[j]));
            }
        }

        return d;
    }

    private double distance(double[] p1, double[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void main(String[] args) {
        Problem4 solver = new Problem4();
        double[][] P = {{0, 0}, {3, 4}, {-5, -3}};

        System.out.println("Brute Result: " + solver.minDistBrute(P));
        System.out.println("Smart Result: " + solver.minDistSmart(P));
    }
}