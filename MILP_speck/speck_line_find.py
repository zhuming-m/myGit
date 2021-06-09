import MILPSbox
class speck():
    def __init__(self, blocksize):
        self.BlockSize = blocksize

    def genVars_InVars_at_Round(self, r):
        assert r >= 1
        return ['p'+str(j)+'R'+str(r) for j in range(self.BlockSize)]

    def rotl(self, X, n, r):
        assert r >= 1
        temp = [None]*n
        for i in range(n-r) :
            temp[i] = X[i+r]
        for i in range(n-r,n) :
            temp[i] = X[i-n+r]
        return temp

    def rotr(self, X, n, r):
        assert r >= 1
        temp = [None]*n
        for i in range(r) :
            temp[i] = X[n-r+i]
        for i in range(r,n) :
            temp[i] = X[i-r]
        return temp

    
    def genVars_Objective(self, r) :
        h = int(self.BlockSize/2)
        assert r >= 1
        return ['pro'+str(i)+'R'+str(r) for i in range(h+1)]

    def genConstraints_of_Round(self, r):
        assert r>=1
        constraints = list()
        X = self.genVars_InVars_at_Round(r)
        Y = self.genVars_InVars_at_Round(r+1)
        h = self.BlockSize
        n = int(h/2)

        x0 = X[0 : n]
        x1 = X[n: 2*n]
        
        y0 = Y[0 : n]
        y1 = Y[n: 2*n]
        
        
        if n == 16 :
            x2 = self.rotr(x0, n, 7)
        else :
            x2 = self.rotr(x0, n, 8)


        x4 = ['x4'+'_'+str(r)+'_'+str(i) for i in range(n)]
        x3 = ['x3'+'_'+str(r)+'_'+str(i) for i in range(n)]
        
        if n == 16 :
            y2 = self.rotr(y1, n, 2)  #y1= self.rotl(y2, n, 2)
        else :
            y2 = self.rotr(y1, n, 3)
                          #三叉分支
        constraints = constraints + MILPSbox.three_way_fork(x1, y2, x4,r,1)
        constraints = constraints + MILPSbox.three_way_fork(x3, y1, y0,r,0)
        d = self.genVars_Objective(r)
        for i in range(n) :     #模加的线性掩码的传播，d[i],d[i+1]为状态转移
            a = [x3[i],x2[i],x4[i]]
            constraints = constraints + [d[i]+' - '+a[0]+' - '+a[1]+' + '+a[2]+' + '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' + '+a[0]+' + '+a[1]+' - '+a[2]+' - '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' + '+a[0]+' - '+a[1]+' - '+a[2]+' + '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' - '+a[0]+' + '+a[1]+' - '+a[2]+' + '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' + '+a[0]+' - '+a[1]+' + '+a[2]+' - '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' - '+a[0]+' + '+a[1]+' + '+a[2]+' - '+d[i+1]+' >= 0']
            constraints = constraints + [a[0]+' - '+d[i]+' + '+a[1]+' + '+a[2]+' + '+d[i+1]+' >= 0']
            constraints = constraints + [d[i]+' + '+a[0]+' + '+a[1]+' + '+a[2]+' + '+d[i+1]+' <= 4']
            
        return constraints
      #目标函数
    def genObjectiveFun_to_Round(self, r):
        assert (r >= 1)
        f = list()
        h = int(self.BlockSize / 2)
        for i in range(1, r+1):
            for j in range(1,h):
                f.append(self.genVars_Objective(i)[j])

        f = ' + '.join(f)
        return f

    def genallvars(self,R):
        cons=list()
        for r in range(1,R+1):
            cons = cons + ['p' + str(j) + 'R' + str(r) for j in range(self.BlockSize)]
            cons = cons + ['pro' + str(i) + 'R' + str(r) for i in range(int(self.BlockSize / 2) + 1)]
            cons = cons + ['x4' + '_' + str(r) + '_' + str(i) for i in range(int(self.BlockSize / 2))]
            cons = cons + ['x3' + '_' + str(r) + '_' + str(i) for i in range(int(self.BlockSize / 2))]
            cons = cons + ['d'  + str(i) + "R" + str(r) for i in range(int(self.BlockSize))]
        cons = cons + ['p' + str(j) + 'R' + str(R+1) for j in range(self.BlockSize)]
        return cons



    def genModel(self, r):
        V = set([])
        C = list([])
        for i in range(1, r+1):
            C = C + self.genConstraints_of_Round(i)
        # V = MILPSbox.getVariables_From_Constraints(C)
        V=self.genallvars(r)
        add_constraint_1 = ' + '.join(['p'+str(i)+'R'+str(1) for i in range(self.BlockSize)]) + ' >= 1'
        # V = V.union(MILPSbox.getVariables_From_Constraints([add_constraint_1]))

        filename='line-'+str(r)+'.lp'
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
        for i in range(1,r+1) :  #初始状态e0
            o.write('pro0R'+str(i)+' = 0')
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
    bar=speck(32)
    bar.genModel(5)

if __name__ == '__main__':
    main()
