import numpy as np
import matplotlib.pyplot as plt

popSize = np.array([20, 50, 80, 150], int)
sigma = np.array([0.001, 0.05, 0.15, 0.3, 0.5, 0.8])
selPressure = np.array([1.0, 1.8])
eps = np.array([0.0, 0.01, 0.1])
popFit = []
simltnSize = 5
dir = "out/Schaffers/crowding/"
format = ".txt"
combinations = [(psize, s, sp, _eps) for _eps in eps for psize in popSize for s in sigma for sp in selPressure]
print(combinations)
score = dict()
for psize, s, sp, _eps in combinations:
    som = 0
    for j in range(1, simltnSize + 1):
        dirctry = dir + "Size_" + str(psize) + "_Sigma_" + str(s) + "_SelPressure_" + str(sp) + "_Eps_" +str(_eps)+"_" + str(j) + format
        lines = np.loadtxt(dirctry, comments="R", delimiter=" ", unpack=False, usecols=1)
        som += lines
    score[(psize, s, sp, _eps)] = som / simltnSize

score = sorted(score.items(), key=lambda x: x[1])
print(max(score, key=lambda key: score[key]))
