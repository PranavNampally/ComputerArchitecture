import matplotlib.pyplot as plt
import numpy as np

from mpl_toolkits.mplot3d import Axes3D

# x, y, z coordinates stored in these lists respectively
probabilities, widths, timeTaken = [], [], []

# open file to read and save coordinates in the lists
with open('data.csv') as result:
	file = result.read()
	file = file.replace(' ', '').replace('\n', ',').split(',')

	i = 0
	while i < len(file) - 3:
		probabilities.append(float(file[i]))
		widths.append(int(file[i+1]))
		timeTaken.append(float(file[i+2]))
		i += 3

# 3d graph plot for x, y, z coordinates
fig = plt.figure()
graph = fig.add_subplot(111, projection='3d', xlabel='Probability', ylabel='Width', zlabel='Time (s)')
graph.plot(xs=probabilities, ys=widths, zs=timeTaken)

plt.savefig('graph.png')