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
}
