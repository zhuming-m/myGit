//
//  main.c
//  demo
//  CRT中国剩余定理
//  Created by 朱明 on 2021/1/23.
//  Copyright © 2021 MAC. All rights reserved.
//

#include <stdio.h>
int Extended_Euclid(int a,int b,int *x,int *y);
int inv(int m, int n, int *x, int *y);
struct pair{
    int divisor;
    int remainder;
}p[10];
int main(int argc, const char * argv[]) {
    // insert code here...
    int n,m=1,M,min,max,sum=0,res_remiander;
    printf("输入个数:");
    scanf("%d",&n); //同余式个数
    //输入同余数的除数和余数
    printf("输入除数和余数:");
    for (int i=0; i<n; i++) {
        scanf("%d%d",&p[i].divisor,&p[i].remainder);
    }
    printf("输入结果值的范围:");
    scanf("%d%d",&min,&max);
    //求m
    for (int i=0; i<n;i++ ) {
        m=m*p[i].divisor;
    }
    //求M[i]的逆并将M[i]*M_1[i]*a[i]和求出来
    int x,y,M_1;
    for (int i=0; i<n; i++) {
        M=m/p[i].divisor;
        M_1=inv(M, p[i].divisor, &x, &y);
        sum=sum+M_1*M*p[i].remainder;
    }
    //结果
    res_remiander=sum%m;
    int count=1,result=0;
    while (!(result>min&&(result<max))) {
        result=count*m+res_remiander;
        count++;
    }
    printf("结果为:%d\n",result);
    return 0;
}
int Extended_Euclid(int a,int b,int *x,int *y){

     int d;
     if(b==0)
      {
          *x=1;
          *y=0;
          return a;
     }
     d=Extended_Euclid(b,a%b,x,y);
     int temp=*x;
     *x=*y;
     *y=temp-(a/b)*(*y);
     return d;
}
int inv(int m, int n, int *x, int *y)
{
    int gcd = Extended_Euclid(m, n, x, y);
    if (1 != gcd)        //说明乘法逆元不存在
    {
        return -1;
    }
    else
    {
        return (*x + n) % n;        //为了使余数一定为正数
    }
}



