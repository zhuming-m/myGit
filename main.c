//
//  main.c
//  Cdemo
//
//  Created by 朱明 on 2019/8/19.
//  Copyright © 2019年 朱明. All rights reserved.
//计算两种方法计算多项式h耗时长

#include <stdio.h>
#include <math.h>
#include <time.h>
#define maxn 10
#define maxk 1e7 //被测函数最大运行次数
double duration;
clock_t start,stop;
double f1(int n,double a[],double x);
double f2(int n,double a[],double x);
int main(int argc, const char * argv[]) {
    // insert code here...
    int i;
    double a[maxn];
    for (i=0; i<maxn; i++) {
        a[i]=(double)i;
    }
    start=clock();
    for(int i=1;i<maxk;i++){
        f1(maxn-1,a,1.1);
    }
    stop=clock();
    duration=((double)(stop-start))/CLOCKS_PER_SEC;
    printf("ticks1=%f\n",(double)(stop-start));
    printf("duration1=%6.2e\n",duration);
    
    start=clock();
    for(int i=1;i<maxk;i++){
        f2(maxn-1,a,1.1);
    }
    stop=clock();
    duration=((double)(stop-start))/CLOCKS_PER_SEC/maxk;
    printf("ticks2=%f\n",(double)(stop-start));
    printf("duration2=%6.2e\n",duration);
    
    return 0;
}

double f1(int n,double a[],double x){
    double p=a[0];
    for(int i=1;i<n;i++){
        p+=a[i]*pow(x, i);
    }
    return p;
}
//秦九韶公式计算
double f2(int n,double a[],double x){
    double p=a[n];
    for(int i=n;i>0;i--){
        p=x*p+a[i-1];
    }
    return p;
}

