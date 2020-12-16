package com.bj58.hds.threadpoolTest;

public class Main {
    public static void main(String[] args){
//        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        Main main = new Main();
        main.testTakeAndPut();
    }

    public void testTakeAndPut(){
        int[] array = {1,4,5,7,8,6,9,0};
        heapSortForPut(7,2,array);
        System.out.println("---------------------");
        int[] array1 = {1,4,5,7,8,6,9};
        heapSortForTake(0,array1[array1.length-1],array1,array1.length);
    }

    public void heapSortForTake(int k, int x, int[] array,int n) {
        if (n > 0) {
            int half = n >>> 1;           // loop while a non-leaf
            while (k < half) {
                int child = (k << 1) + 1; // assume left child is least
                int c = array[child];
                int right = child + 1;
                if (right < n && (c >= array[right]))
                    c = array[child = right];
                if (x <= c)
                    break;
                array[k] = c;
                k = child;
                printArray(array);
            }
            array[k] = x;
            printArray(array);
        }
    }

    public void heapSortForPut(int k, int x, int[] array) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            int e = array[parent];
            if (x>=e)
                break;
            array[k] = e;
            k = parent;
            printArray(array);
        }
        array[k] = x;
        printArray(array);
    }

    public static void printArray(int[] array){
        for(int num:array){
            System.out.print(num+" ");
        }
        System.out.println();
    }
}
