file_name = 'equations.txt'
f = open(file_name,'r')
f1=open("coefficient.txt",'w')
k = f.readlines()
for i in k:
    i=i.strip()
    i=i.replace(" ","")
    i=i.strip("Aninequality")
    i=i.strip('0')
    i=i.replace(">=",']')
    i=i.replace(")x+",',')
    i=i.replace('(','[')
    f1.write(i)
    f1.write("\n")
