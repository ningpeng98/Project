package com.test.cases;

import com.test.Case;
import com.test.annotations.Benchmark;
import com.test.annotations.Measurement;

import java.util.Arrays;
import java.util.Random;

/**
 * 测试自己实现的归并排序，快速排序与Arrays.sort的耗时对比
 *
 * */

@Measurement(iterations = 10,group = 3)
public class SortCase implements Case {
    public void quickSort(int[] a){
        //1.确定基准值
        quickSortInternal(a,0,a.length-1);

    }
    //待排序区间是[low,high]
    private void quickSortInternal(int[] a,int low,int high){
        if(high <= low){
            return;
        }
        //得到基准值最终所在的下标
        int[] pivotIndexs = parition(a,low,high);

        quickSortInternal(a,low,pivotIndexs[0]);
        quickSortInternal(a,pivotIndexs[1],high);
    }

    private void swap (int[] a,int i,int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private int[] parition(int[] a,int low,int high){
        int pivot = a[high];
        int less = low;
        int i = low;
        int more = high;
        while(i < more){
            if(a[i] == pivot){
                i++;
            }else if(a[i] < pivot){
                swap(a,i,less);
                i++;
                less++;
            }else{
                while(more > i && a[more] > pivot){
                    more--;
                }
                swap(a,i,more);
            }
        }
        return new int[]{less-1,more};
    }


    public void mergeSort(int[] a){

        mergeSortInternal(a,0,a.length);

    }
    private void mergeSortInternal(int[] a,int low,int high){
        if(high <= low+1){
            return;
        }
        int mid = (low+high) >> 1;
        mergeSortInternal(a,low,mid);
        mergeSortInternal(a,mid,high);
        merge(a,low,mid,high);

    }

    private void merge(int[] a,int low,int mid,int high){
        int length = high-low;
        int[] extra = new int[length];
        int i = low;
        int j = mid;
        int k =  0;
        while(i<mid && j<high){
            if(a[i]<=a[j]){
                extra[k++] = a[i++];
            }else{
                extra[k++] = a[j++];
            }
        }

        while(i < mid){
            extra[k++] = a[i++];
        }

        while(j < high){
            extra[k++] = a[j++];
        }
        System.arraycopy(extra,0,a,low,length);
    }
    //测试自己写的快速排序
    @Benchmark
    public void testQuickSort(){
        int[] a = new int[10000];
        Random random = new Random(201910713);
        for(int i = 0;i < a.length;i++){
            a[i] = random.nextInt(100000);
        }
        quickSort(a);
    }

    //测试自己写的归并排序
    @Benchmark
    public void testMergeSort(){
        int[] a = new int[10000];
        Random random = new Random(201910713);
        for(int i = 0;i < a.length;i++){
            a[i] = random.nextInt(100000);
        }
        mergeSort(a);
    }
    //测试Arrays.sort
    @Benchmark
    public void testArraysSort(){
        int[] a = new int[10000];
        Random random = new Random(201910713);
        for(int i = 0;i < a.length;i++){
            a[i] = random.nextInt(100000);
        }
        Arrays.sort(a);
    }



}
