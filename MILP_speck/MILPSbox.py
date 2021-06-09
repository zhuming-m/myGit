def three_way_fork(x, y, z, r,right):
    """
    Constranints for three-fork branch
    X + Y + Z - 2 dummy >= 0
    X + Y + Z <= 2
    dummy - X >= 0
    dummy - Y >= 0
    dummy - Z >= 0
    right=1表示右边三叉分支
    right=0表示左边三叉分支
    """
    constraints = []
    for i in range(len(x)):
        constraints += [x[i] + ' + ' + y[i] + ' + ' + z[i] + ' <= 2']
        constraints += [x[i] + ' + ' + y[i] + ' + ' + z[i] + ' - 2 d' + str(i+16*right) + 'R' + str(r) + ' >= 0']
        constraints += ['d' + str(i+16*right) + "R" + str(r) + ' - ' + x[i] + ' >= 0']
        constraints += ['d' + str(i+16*right) + "R" + str(r) + ' - ' + y[i] + ' >= 0']
        constraints += ['d' + str(i+16*right) + "R" + str(r) + ' - ' + z[i] + ' >= 0']
    return constraints

#把约束条件里变量拿出来
def getVariables_From_Constraints(C):
    V = set([])
    for s in C:
        temp = s.strip()
        temp = temp.replace('+', ' ')
        temp = temp.replace('-', ' ')
        temp = temp.replace('>=', ' ')
        temp = temp.replace('<=', ' ')
        temp = temp.split()
        for v in temp:   #将约束条件里的数字给排除
           if not v.isdigit():
              V.add(v)
    return V
