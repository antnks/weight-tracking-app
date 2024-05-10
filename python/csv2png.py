import matplotlib
matplotlib.use('Agg')

import matplotlib.pyplot as plt
import csv
import sys
from datetime import datetime

# sudo apt-get install python3-matplotlib

x = list()
y = list()

if len(sys.argv) < 4:
	print("Usage:", sys.argv[0], "path_to_csv label path_to_output [group_by_date]")
	exit(1)

with open(sys.argv[1],"r") as csvfile:
	rows = csv.reader(csvfile, delimiter = ',')

	for row in rows:
		x.append(row[0])
		y.append(float(row[1]))

if len(sys.argv) > 4 and len(sys.argv[4]) > 0:
	count = 0
	temp = dict()
	for dot in x:
		d = datetime.fromisoformat(x[count])
		s = d.strftime("%Y-%m-%d")
		if s not in temp:
			temp[s] = list()
		temp[s].append(y[count])
		count = count + 1

	xd = list()
	yd = list()
	for i in temp:
		xd.append(i)
		yd.append(sum(temp[i])/len(temp[i]))
	x = xd
	y = yd

plt.plot(x, y, color = "g", linestyle = "dashed", marker = "o",label = sys.argv[2])

plt.xlabel("date")
plt.xticks(rotation=90)
plt.ylabel(sys.argv[2])
plt.title(sys.argv[1])
plt.legend()
plt.savefig(sys.argv[3], dpi=300, bbox_inches = "tight")

