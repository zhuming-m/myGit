from MILPSbox import *
from ConstrainGenerator import *


class speck():
    def __init__(self, blocksize):   #分组长度
        self.BlockSize = blocksize

    def genVars_InVars_at_Round(self, r):
        assert r >= 1      #r>=1时才执行
        if r == 1:         #p0....p31变量
            return ['p'+str(j) for j in range(self.BlockSize)]
        if r > 1 :         #p0Rd(r-1)....p31Rd(r-1)
            return ['p'+str(j)+'Rd'+str(r-1) for j in range(self.BlockSize)]
        
    def rotl(self, X, n, r):  #temp=[x[r],x[r+1],...,x[n-1],x[0],...,x[r-1]]循环左移r位
        assert r >= 1
        temp = [None]*n
        for i in range(n-r) :
            temp[i] = X[i+r]
        for i in range(n-r,n) :
            temp[i] = X[i-n+r]
        return temp
    
    def rotr(self, X, n, r):  #temp=[x[n-r],..x[n-1],x[0],...,x[n-r-1]]循环右移r位
        assert r >= 1
        temp = [None]*n
        for i in range(r) :
            temp[i] = X[n-r+i]
        for i in range(r,n) :
            temp[i] = X[i-r]
        return temp
    
    def genVars_Objective(self, r) :
        h = self.BlockSize
        n = int(h/2)
        assert r >= 1          #pro0Rd(r-1)...pro15Rd(r-1)
        return ['pro'+str(i)+'Rd'+str(r-1) for i in range(n-1)]
    
    def genConstraints_of_Round(self, r):
        assert r>=1
        constraints = list()
        X = self.genVars_InVars_at_Round(r)    #x=[p0Rd(r-1),...,p31Rd(r-1)]
        Y = self.genVars_InVars_at_Round(r+1)  #y=[p0Rd(r),...,p31Rd(r)]
        h = self.BlockSize
        n = int(h/2)
        x0 = X[0 :   n]   #左半边和右半边
        x1 = X[n : 2*n]
        y0 = Y[0 :   n]
        y1 = Y[n : 2*n]
        if n == 16 :
            x2 = self.rotr(x0, 16, 7)  #循环移位
            x3 = self.rotl(x1, 16, 2)
        else :
            x2 = self.rotr(x0, n, 8)
            x3 = self.rotl(x1, n, 3)

        #constraints = constraints + ConstraintGenerator.xorConstraints(x3, y1, y0, r)
        constraints = constraints + xorConstraints(x3, y1, y0, r)

        d = self.genVars_Objective(r)     #d=pro0Rd(r-1)...pro15Rd(r-1)，模加加差分概率

        for i in range(n-1) :    #模加操作建模x2[0,...,n-1]n-1为低位(a[i+1],b[i+1],c[i+1],a[i],b[i],c[i],p)
            b = [x2[i],x1[i],y0[i]]
            a = [x2[i+1],x1[i+1],y0[i+1]]   #可能的模加差分第二条件线性不等式,将!eq(a[i],b[i],c[i])添加到差分模式的向量中去
            constraints = constraints + [a[1]+' - '+a[2]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[0]+' - '+a[1]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[2]+' - '+a[0]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[0]+' + '+a[1]+' + '+a[2]+' + '+d[i]+' <= 3 ']
            constraints = constraints + [a[0]+' + '+a[1]+' + '+a[2]+' - '+d[i]+' >= 0 ']
            constraints = constraints + [b[0]+' + '+b[1]+' + '+b[2]+' + '+d[i]+' - '+a[1]+' >= 0 ']
            constraints = constraints + [a[1]+' + '+b[0]+' - '+b[1]+' + '+b[2]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[1]+' - '+b[0]+' + '+b[1]+' + '+b[2]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[0]+' + '+b[0]+' + '+b[1]+' - '+b[2]+' + '+d[i]+' >= 0 ']
            constraints = constraints + [a[2]+' - '+b[0]+' - '+b[1]+' - '+b[2]+' + '+d[i]+' >= -2 ']
            constraints = constraints + [b[0]+' - '+a[1]+' - '+b[1]+' - '+b[2]+' + '+d[i]+' >= -2 ']
            constraints = constraints + [b[1]+' - '+a[1]+' - '+b[0]+' - '+b[2]+' + '+d[i]+' >= -2 ']
            constraints = constraints + [b[2]+' - '+a[1]+' - '+b[0]+' - '+b[1]+' + '+d[i]+' >= -2 ']
          #可能模加差分的第一条件a[n-1]+b[n-1]+c[n-1]=0
        constraints = constraints + [x2[n-1] +' + '+x1[n-1]+' + '+y0[n-1]+' <= 2 ']
        constraints = constraints + [x2[n-1] +' + '+x1[n-1]+' + '+y0[n-1]+' - 2 temp'+str(r-1)+' >= 0 ']
        constraints = constraints + ['temp'+str(r-1)+' - '+x2[n-1]+' >= 0 ']
        constraints = constraints + ['temp'+str(r-1)+' - '+x1[n-1]+' >= 0 ']
        constraints = constraints + ['temp'+str(r-1)+' - '+y0[n-1]+' >= 0 ']
        
        return constraints

    def genObjectiveFun_to_Round(self, r):  #模加操作在左半边
        assert (r >= 1)
        h = self.BlockSize
        n = int(h/2)
        f = list([])
        for i in range(1, r+1):    #这里pro0Rd(0)=!eq(a[i],b[i],c[i])
            for j in range(n-1):  #f=pro0Rd(0),...,pro15Rd(0),...,pro0Rd(r),...pro15Rd(r-1)左半边r轮的变量
                f.append(self.genVars_Objective(i)[j])

        f = ' + '.join(f)         #f=pro0Rd(0)+...+pro15Rd(0)+...+pro0Rd(r)+...+pro15Rd(r-1)
        return f
    
    def genModel(self, r):
        V = set([])
        C = list([])
        for i in range(1, r+1):
            C = C + self.genConstraints_of_Round(i)
       # V = BasicTools.getVariables_From_Constraints(C)   #p0+...+p31>=1

        V = getVariables_From_Constraints(C)
        add_constraint_1 = ' + '.join(['p'+str(i) for i in range(self.BlockSize)]) + ' >= 1'
       # V = V.union(BasicTools.getVariables_From_Constraints([add_constraint_1]))

        V = V.union(getVariables_From_Constraints([add_constraint_1]))
        filename='speck'+str(self.BlockSize)+'diff-round-'+str(r)+'.lp'
        o=open(filename,'w')
        o.write('Minimize')
        o.write('\n')
        o.write(self.genObjectiveFun_to_Round(r))
        o.write('\n')
        o.write('\n')
        o.write('Subject To')
        o.write('\n')
        o.write(add_constraint_1)
        o.write('\n')
        for c in C:
            o.write(c)
            o.write('\n')
        o.write('\n')
        o.write('\n')
        o.write('Binary')
        o.write('\n')
        for v in V:
            o.write(v)
            o.write('\n')
        o.close()     


def main():
    print('Initialized...')
    bar = speck(32)
    bar.genModel(5)
    bar.genModel(6)
    bar.genModel(9)

if __name__ == '__main__':
    main()
