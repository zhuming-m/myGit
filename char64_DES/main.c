//
//  main.c
//  test
//
//  Created by MAC on 2020/10/22.
//  Copyright © 2020 MAC. All rights reserved.
//
#include <stdio.h>
#include "tables.h"
#include <string.h>
void MYXOR(char *in1,char *in2,char *out,int n);
void IP(char *plaintext,char *output,char *ip);
void subkey(char *K,char (*sk)[]);
void Extend(char *in,char *Eout);
int  transfer2to10(char *str,int n);
void transfer10to2(char s,char str2[]);
void function(char *in,char *key,char sboxoutput_p[]);
void ENCstructure(char *cipher,char *K,char *M);
void DENC(char *plaintext,char *K,char *cipher);
int main(int argc, const char * argv[]) {
    // insert code here...
    char s=sbox[0][15];
    printf("%d",s);
    char* M="0000000100100011010001010110011110001001101010111100110111101111";
    char *K="0001001100110100010101110111100110011011101111001101111111110001";
    printf("待加密的明文为: %s\n主密钥为:  %s\n",M,K);
    char cipher[65];
    ENCstructure(cipher,K,M);
    printf("加密后密文为:  %s\n%lu\n",cipher,strlen(cipher));
    char plaintext[65];
    DENC(plaintext, K, cipher);
    printf("解密后明文为:  %s\n%lu\n",plaintext,strlen(plaintext));
}
void DENC(char *plaintext,char *K,char *cipher){
       char sk[16][49];
    //生成子密钥存在sk中
    subkey(K, sk);
    char output[65];
    //初始ip置换
    IP(cipher, output, ipermutation);
    char L[33];
    strncpy(L, output, 32);
    L[32]='\0';
    char R[33];
    strcpy(R, output+32);
    for (int i=0; i<16; i++) {
        if(i==15){      //最后一轮不交换
            char FR[33];
            function(R, sk[15-i], FR);
            MYXOR(L, FR, L, 32);
            break;
        }
        char FR[33];
        function(R, sk[15-i], FR);
        char L_F[33];
        MYXOR(L, FR, L_F, 32);  //R=L+f(R,K);
        //L和L异或f(R)交换位置
        for(int j=0;j<32;j++)
            L[j]=R[j];
        for(int j=0;j<32;j++){
            R[j]=L_F[j];
        }

    }
    char lun16[65];
    strcpy(lun16, L);
    strcpy(lun16+32, R);
    //ip的逆置换
    IP(lun16, plaintext, re_ipermutation);

}
void ENCstructure(char *cipher,char *K,char *M){
    char sk[16][49];
    subkey(K, sk);
    char output[65];
    IP(M, output, ipermutation);
    char L[33];
    strncpy(L, output, 32);
    L[32]='\0';
    char R[33];
    strcpy(R, output+32);
    for (int i=0; i<16; i++) {
        if(i==15){      //最后一轮不交换
            char FR[33];
            function(R, sk[i], FR);
            MYXOR(L, FR, L, 32);
            printf("第%d轮加密后的L: %s\n第%d轮加密后的R: %s\n",i,L,i,R);
            break;
        }
        char FR[33];
        function(R, sk[i], FR);
        char L_F[33];
        MYXOR(L, FR, L_F, 32);  //R=L+f(R,K);
        //L和L异或f(R)交换位置
        for(int j=0;j<32;j++)
            L[j]=R[j];
        for(int j=0;j<32;j++){
            R[j]=L_F[j];
        }
        printf("第%d轮加密后的L: %s\n第%d轮加密后的R: %s\n",i,L,i,R);

    }
    char lun16[65];
    strcpy(lun16, L);
    strcpy(lun16+32, R);
    //ip的逆置换
    IP(lun16, cipher, re_ipermutation);
}
//异或
void MYXOR(char *in1,char *in2,char *out,int n){
    for (int i=0; i<n; i++) {
        if(in1[i]!=in2[i]){
            out[i]='1';
        }else{
            out[i]='0';
        }
    }
    out[n]='\0';
}
//初始置换IP，置换1～64，数组0～63
void IP(char *plaintext,char *output,char *ip){
    int i=0;
        for (; i<64; i++) {
        *(output+i)=*(plaintext+ip[i]-1);
    }
    *(output+i)='\0';
}
//子密钥生成,存放在sk中
void subkey(char *K,char sk[16][49]){
    //char *K="0001001100110100010101110111100110011011101111001101111111110001";
    
    char out[57];
    int i=0;
    for (; i<56; i++) { //pc_1置换
        *(out+i)=*(K+pc_1[i]-1);
    }
    *(out+i)='\0';
    //printf("%s\n",out);
    char C[57],D[29];
    strcpy(C, out);
    strcpy(D, out+28);
    int shiftimes[16]={1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    //16轮子密钥生成
    for(int j=0;j<16;j++){
        for(int i=0;i<shiftimes[j];i++){  //每轮循环移位位数
            char tmp1=C[0],tmp2=D[0];
            for (int k=0; k<27; k++) {
                C[k]=C[k+1];
                D[k]=D[k+1];
            }
            C[27]=tmp1;
            D[27]=tmp2;
        }
        //C和D合并成CD
        char CD[57];
        strncpy(CD,C,28);
        strcpy(CD+28,D);
        //pc-2置换生成m子密钥然后放入sk中
        for (int l=0; l<48; l++) {
            sk[j][l]=CD[pc_2[l]-1];
        }
    }
}
//E扩展置换
void Extend(char *in,char *Eout){
    for (int i=0; i<48; i++) {
        Eout[i]=in[E[i]-1];
    }
    Eout[48]='\0';
}
//2进制数转成10进制数
int  transfer2to10(char *str,int n){
    int s=0,p=1;
    for (int i=n-1; i>=0; i--) {
        int x=str[i]-'0';
        s+=x*p;
        p*=2;
    }
    return s;
}
//查s盒得到的10进制数转化成2进制数
void transfer10to2(char s,char str2[]){
    int num=s;
    int shang=num/2;
    int yushu=num%2;
        if(yushu==0){
            str2[3]='0';
        }else
            str2[3]='1';
        //str2[3-i]=(yushu==0?'0':'1');
    for (int i=1; i<4; i++) {
        yushu=shang%2;
        shang=shang/2;
        if(yushu==0){
            str2[3-i]='0';
        }else
            str2[3-i]='1';

    }
    str2[4]='\0';

}
//轮函数
void function(char *in,char *key,char sboxoutput_p[]){
    char eout[49];
    Extend(in, eout);  //E扩展
    char xorout[49];
    MYXOR(eout, key, xorout, 48);//轮密钥加
    char sboxoutput[33];    //进过s盒之后的32比特输出,没进过p置换还
    //每个6bit串经过s盒后得到4bit串str4
    for (int i=0; i<8; i++) {
        char b16[3],b25[5];
        b16[0]=xorout[6*i];
        b16[1]=xorout[6*i+5];
        b16[2]='\0';
        b25[0]=xorout[6*i+1];
        b25[1]=xorout[6*i+2];
        b25[2]=xorout[6*i+3];
        b25[3]=xorout[6*i+4];
        b25[4]='\0';
        int row=transfer2to10(b16, 2);
        int col=transfer2to10(b25, 4);
        char sboxout=sbox[i][16*row+col]; //取出对应s盒的对应行列的数据
        char str4[5];
        transfer10to2(sboxout, str4);
       // strcat(sboxoutput,str4);
        for (int j=0 ; j<4; j++) {
            sboxoutput[4*i+j]=str4[j];
        }
        sboxoutput[32]='\0';
    }
//    char sboxoutput_p[33];
    //p置换
    for (int i=0; i<32; i++) {
        sboxoutput_p[i]=sboxoutput[p[i]-1];
    }
}
