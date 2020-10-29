//
//  main.c
//  allocatememory
//   realloc()如果原来的内存后面还有足够多剩余内存，或者，重新分配小于原来的大小，realloc还是返回原来内存的地址;
/*  如果有⾜够空间⽤于扩⼤mem_address指向的内存块，则分配额外内存，并返回mem_address,这⾥说的是“扩⼤”，我们知道，realloc是从堆上分配内存的，当扩⼤⼀块内存空间时，realloc()试图直接从堆上现存的数据后⾯的那些字节中获得附加的字节，如果能够满⾜，⾃然天下太平。也就是说，如果原先的内存⼤⼩后⾯还有⾜够的空闲空间⽤来分配，加上原来的空间⼤⼩= newsize。那么就ok。得到的是⼀块连续的内存。2、如果原先的内存⼤⼩后⾯没有⾜够的空闲空间⽤来分配，那么从堆中另外找⼀块newsize⼤⼩的内存。并把原来⼤⼩内存空间中的内容复制到newsize中。返回新的mem_address指针。（数据被移动了）*/
//  Created by 朱明 on 2019/9/4.
//  Copyright © 2019年 朱明. All rights reserved.
//

#include <stdio.h>
#include "stdlib.h"
int main(int argc, const char * argv[]) {
    // insert code here...
    
    int n,*p,*p1;
    printf("input n:\n");
    scanf("%d",&n);
    if((p=(int *)malloc(n*sizeof(int)))==NULL){
        printf("fail allocation!");
        exit(1);
    }
    //输入整数的个数
    printf("enter %d number:",n);
    for (int i=0; i<n; i++) {
        scanf("%d",p+i);
    }
    for(int i=0;i<n;i++){
        printf("%d\t",*(p+i));
    }
    //更改内存分配,缩小内存到n-1,输出内容
    if((p1=(int *)realloc(p, (n-1)*sizeof(int)))==NULL){
        printf("fail alloction!");
        exit(1);
    }
    printf("\n");
    int length=sizeof(p1)/4;
    for (int i=0; i<length; i++) {
        printf("%d\t",*(p1+i));
    }
    free(p1);
    return 0;
}
