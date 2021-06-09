listA=[]
file_name = 'coefficient.txt'
f = open(file_name,'r')
k = f.readlines()
for a in k:
    a=a.strip()
    a = a.strip('[')
    a = a.strip(']')
    a = a.split(',')
    listA.append(a)
listX = []
file_name = 'impossible.txt'
f = open(file_name,'r')
k = f.readlines()
for a in k:
    a=a.strip()
    a=a.replace(" ",'')
    a = a.strip("]")
    a = a.strip("[")
    a = a.split(',')
    listX.append(a)
while listX:
    flag_list = []
    del_list = []
    copy_list = []
    for A_item in listA:  #取一个线性不等式
        flag = 0
        for X_item in listX:   #排除的不可能的模式
            count = 0
            for k in range(5):
                count = count + int(A_item[k])*int(X_item[k])
            count = count + int(A_item[5])
            if count < 0:
                flag =flag + 1  #排除不可能的模式的个数
        flag_list.append(flag)
    del_list = listA[flag_list.index(max(flag_list))]
    listA.pop(flag_list.index(max(flag_list)))
    for X_item in listX:
        del_count=0
        for k in range(5):
            del_count = del_count + int(del_list[k])*int(X_item[k])
        del_count = del_count + int(del_list[5])
        if del_count < 0:
            copy_list.append(X_item)
    for item in copy_list:
        listX.remove(item)
    print(list(map(int,del_list)))

