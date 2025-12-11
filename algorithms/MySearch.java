package algorithms;
public class MySearch {
    // linear search
    public static <T> int linearSearch(T[] array, T data) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(data)) {
                return i;
            }
        }
        return -1;
    }

    // binary search
    public static <T extends Comparable<T>> int binarySearch(T[] array, T data) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = array[mid].compareTo(data);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; 
            }
        }
        return -1;
    }
}
