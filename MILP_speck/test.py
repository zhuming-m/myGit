from gurobipy import *
m=read("line-5.lp")
m.optimize()
m.write("line-r5.sol")

# m=read("line-2.lp")
# m.optimize()
# m.write("speck32line-2.sol")


