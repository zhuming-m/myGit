def xorConstraints(x,y,z,r):
        """
        Constranints for xor
        X + Y + Z - 2 dummy >= 0
        X + Y + Z <= 2
        dummy - X >= 0
        dummy - Y >= 0
        dummy - Z >= 0
        """
        constraints = []
        for i in range(len(x)):
            constraints += [x[i] + ' + ' + y[i] + ' + ' + z[i] + ' <= 2']
            constraints += [x[i] + ' + ' + y[i] + ' + ' + z[i] + ' - 2 temp' + str(i) + 'Rnd' + str(r - 1) + ' >= 0']
            constraints += ['temp' + str(i) + "Rnd" + str(r - 1) + ' - ' + x[i] + ' >= 0']
            constraints += ['temp' + str(i) + "Rnd" + str(r - 1) + ' - ' + y[i] + ' >= 0']
            constraints += ['temp' + str(i) + "Rnd" + str(r - 1) + ' - ' + z[i] + ' >= 0']
        return constraints