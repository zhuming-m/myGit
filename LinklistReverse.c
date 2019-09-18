//
//  main.c
//  LinkListReverse
//
//  Created by 朱明 on 2019/9/18.
//  Copyright © 2019年 朱明. All rights reserved.
//

#include <stdio.h>
#include "stdlib.h"
typedef int Elemtype;
typedef struct Lnode{
    Elemtype data;
    struct Lnode *next;
}Lnode,*LinkList;
LinkList init(void);
void reverse(LinkList L);
void print(LinkList L);
int main(int argc, const char * argv[]) {
    
    LinkList L=init();
    reverse(L);
    print(L);
    return 0;
}
LinkList init(){
    //头结点
    Lnode *L;
    L=(Lnode *)malloc(sizeof(Lnode));
    Lnode *q=L;
    int i=0;
    printf("ENTER numbers:");
    while(i<5){
    
    Lnode *p=(Lnode *)malloc(sizeof(Lnode));
    scanf("%d",&(p->data));
    p->next=NULL;
    q->next=p;
    q=q->next;
    i++;
    }
    return L;
}
void  reverse(LinkList L){
    Lnode *s,*r;
    s=r=L;
    while(r->next!=NULL){
        r=r->next;
    }
    s=L;//头节点
    //用尾结点r将第一个节点依次插入到r后面
    while (L->next!=r) {
    
    s=L->next;
    L->next=s->next;
    s->next=r->next;
    r->next=s;
    }
    
}
void print(LinkList L){
    Lnode *p;
    p=L;
    while(p->next!=NULL){
        p=p->next;
        printf("%d\t",p->data);
    }
}
