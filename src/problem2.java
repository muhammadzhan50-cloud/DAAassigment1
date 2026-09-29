public class Problem2 {

    public double getMedianBrute(int[] A, int[] B) {
        int n = A.length;
        int m = B.length;
        int[] merged = new int[n + m];

        int i = 0, j = 0, k = 0;
        while (i < n && j < m) {
            if (A[i] <= B[j]) {
                merged[k++] = A[i++];
            } else {
                merged[k++] = B[j++];
            }
        }
        while (i < n) {
            merged[k++] = A[i++];
        }
        while (j < m) {
            merged[k++] = B[j++];
        }

        int totalLength = n + m;
        if (totalLength % 2 == 1) {
            return merged[totalLength / 2];
        } else {
            return (merged[totalLength / 2 - 1] + merged[totalLength / 2]) / 2.0;
        }
    }

    public double getMedianSmart(int[] A, int[] B) {
        if (A.length > B.length) {
            return getMedianSmart(B, A);
        }

        int n = A.length;
        int m = B.length;
        int low = 0;
        int high = n;

        while (low <= high) {
            int partitionA = low + (high - low) / 2;
            int partitionB = (n + m + 1) / 2 - partitionA;

            int maxLeftA = (partitionA == 0) ? Integer.MIN_VALUE : A[partitionA - 1];
            int minRightA = (partitionA == n) ? Integer.MAX_VALUE : A[partitionA];

            int maxLeftB = (partitionB == 0) ? Integer.MIN_VALUE : B[partitionB - 1];
            int minRightB = (partitionB == m) ? Integer.MAX_VALUE : B[partitionB];

            if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
                if ((n + m) % 2 == 1) {
                    return Math.max(maxLeftA, maxLeftB);
                } else {
                    return (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0;
                }
            } else if (maxLeftA > minRightB) {
                high = partitionA - 1;
            } else {
                low = partitionA + 1;
            }
        }

        return 0.0;
    }

    public static void main(String[] args) {
        Problem2 p2 = new Problem2();

        int[] A1 = {2, 4};
        int[] B1 = {3};
        System.out.println("Ex 1 Brute: " + p2.getMedianBrute(A1, B1));
        System.out.println("Ex 1 Smart: " + p2.getMedianSmart(A1, B1));

        int[] A2 = {2, 4};
        int[] B2 = {3, 5};
        System.out.println("Ex 2 Brute: " + p2.getMedianBrute(A2, B2));
        System.out.println("Ex 2 Smart: " + p2.getMedianSmart(A2, B2));
    }
}