package algorithms;

public class MySort {

    // selection sort 
    public static <T extends Comparable<T>> void selectionSort(T[] array){
        for(int i = 0; i < array.length - 1; i++){
            int min = i;
            for(int j = i + 1; j < array.length; j++){
                if(array[min].compareTo(array[j]) > 0){
                    min = j;
                }
            }
            T temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
    }

    // merge sort 
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void mergeSort(T[] array){
        int length = array.length;
        if(length < 2){
            return;
        }
        int midIndex = length / 2;
        T[] leftArr = (T[]) new Comparable[midIndex];
        T[] rightArr = (T[]) new Comparable[length - midIndex];

        for(int i = 0; i < midIndex; i++){
            leftArr[i] = array[i];
        }

        for(int i = midIndex; i < length; i++){
            rightArr[i - midIndex] = array[i];
        }

        mergeSort(leftArr);
        mergeSort(rightArr);

        merge(array, leftArr, rightArr);
    }

    private static <T extends Comparable<T>> void merge(T[] array, T[] leftArr, T[] rightArr){
        int leftSize = leftArr.length;
        int rightSize = rightArr.length;

        int i = 0; // this is for leftArr
        int j = 0; // this is for rightArr
        int k = 0; // this is for Array

        while(i < leftSize && j < rightSize){
            if(leftArr[i].compareTo(rightArr[j]) <= 0){
                array[k] = leftArr[i];
                i++;
            } else{
                array[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while(i < leftSize){
            array[k] = leftArr[i];
            i++;
            k++;
        }

        while(j < rightSize){
            array[k] = rightArr[j];
            j++;
            k++;
        }
    }
}
