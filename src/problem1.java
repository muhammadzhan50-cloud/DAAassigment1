public class Problem1 {

    public int countFreqBrute(int key, int[] A) {
        if (A == null) {
            return 0;
        }

        int count = 0;
        for (int num : A) {
            if (num == key) {
                count++;
            }
        }
        return count;
    }

    public int countFreqSmart(int key, int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int first = findBound(A, key, true);
        if (first == -1) {
            return 0;
        }

        int last = findBound(A, key, false);
        return last - first + 1;
    }

    private int findBound(int[] A, int key, boolean findFirst) {
        int low = 0;
        int high = A.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (A[mid] == key) {
                result = mid;
                if (findFirst) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else if (A[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Problem1 p1 = new Problem1();

        int[] A1 = {1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4, 5, 5, 5, 5};
        System.out.println("Test 1 (key = 4): " + p1.countFreqSmart(4, A1));
        System.out.println("Test 2 (key = 3): " + p1.countFreqSmart(3, A1));
    }
}