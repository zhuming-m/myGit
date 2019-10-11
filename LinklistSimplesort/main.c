//
//  main.c
//  LinklistSimplesort
//
//  Created by 朱明 on 2019/10/11.
//  Copyright © 2019年 朱明. All rights reserved.
//

#include <stdio.h>
#include "stdlib.h"
typedef struct Lnode{
    int data;
    struct Lnode *next;
}Lnode;
Lnode * create(int n);
void simplesort(Lnode *L);
void print(Lnode *L);
int main(int argc, const char * argv[]) {
    // insert code here...
    int n=5;
    Lnode *L=create(n);
    print(L);
    printf("\n");
    simplesort(L);
    print(L);
    return 0;
}
//尾插法建表
Lnode * create(int n){
    Lnode *L;
    L=(Lnode *)malloc(sizeof(Lnode));
    Lnode *q=L;  //q尾指针 
    int i=0;
    printf("ENTER numbers:");
    while(i<n){
        Lnode *p=(Lnode *)malloc(sizeof(Lnode));
        scanf("%d",&(p->data));
        p->next=NULL;
        q->next=p;
        q=q->next;
        i++;
    }
    return L;
}
//链表简单选择排序
void simplesort(Lnode *L){
    Lnode *p,*q,*t = NULL;
    q=L->next;
    while (q->next!=NULL) {
        int min=q->data;
        p=q;     //每次p从q的下一个开始遍历
        t=q;     //每次t重置为q,防止后面没有比q->data更小的值,而导致后面交换的上次循环*t保留的值,不是这次的最小值;
        while (p->next!=NULL) {
            if(min>p->next->data){
                min=p->next->data;
                t=p->next; //t指向最小值节点
            }
            p=p->next;
        }
        if(t!=q){         //t=q不需要交换,q后面没有比它小的节点
        t->data=q->data;  //将q后面找到的最小值节点值与q进行交换
        q->data=min;
        }
        q=q->next;
    }
}

void print(Lnode *L){
    Lnode *p;
    p=L;
    while(p->next!=NULL){
        p=p->next;
        printf("%d\t",p->data);
    }
}
