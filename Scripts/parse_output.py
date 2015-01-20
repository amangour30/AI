
import re
fo = open("final_output", "r")
ns = open("node_step.txt", "w")


for line in fo.readlines():
	if "Nodes Expanded" in line:
		words=line.split(' ');
		ns.write(words[11] + ", " + words[12] + "\n")

