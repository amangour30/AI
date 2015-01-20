#!/bin/python3
from itertools import permutations

l=[0, 1, 2, 3, 4, 5, 6, 7, 8]
print(l)
count=0

f = open('perms.txt', 'w')
for indices in permutations(l, 9):
	count=count+1
	var = ' '.join(str(e) for e in indices)
	#print(var)
	f.write(var + '\n')

print(count)




