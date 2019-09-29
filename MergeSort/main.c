//
//  main.c
//  MergeSort
//  2路归并排序，分治法 时间复杂度O(nlogn)
//  Created by 朱明 on 2019/9/29.
//  Copyright © 2019年 朱明. All rights reserved.
//

#include <stdio.h>
#include "stdlib.h"
typedef  int Elemtype ;
void merge(Elemtype a[],int low,int mid,int high);
void MergeSort(Elemtype a[],int low,int high);
int main(int argc, const char * argv[]) {
    // insert code here...
    Elemtype a[]={3,2,6,1,7,5,4,9,8};
    int low=0,high=8;
    MergeSort(a, low, high);
    for(int i=low;i<=high;i++){
        printf("%d,",a[i]);
    }
    return 0;
}
//有序序列a[low..mid]和a[mid+!..high]做合并，用了一个辅助空间b
void merge(Elemtype a[],int low,int mid,int high){
    Elemtype *b=(int *)malloc((high+1)*sizeof(int));
    int i,j,k;
    for(k=low;k<=high;k++){b[k]=a[k];}
    for(i=low,j=mid+1,k=i;i<=mid&&j<=high;k++){
        if(b[i]<=b[j]){
            a[k]=b[i++];
        }else{
            a[k]=b[j++];
        }
    }
    while(i<=mid)  a[k++]=b[i++];
    while(j<=high) a[k++]=b[j++];
}
//递归实现排序
void MergeSort(Elemtype a[],int low,int high){
    if(low<high){
        int mid=(low+high)/2;
        MergeSort(a,low,mid);//前部分排序
        MergeSort(a,mid+1,high);//后部分排序
        merge(a,low,mid,high);//合并
    }
}
