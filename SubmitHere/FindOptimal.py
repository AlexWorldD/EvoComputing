import numpy as np
import matplotlib.pyplot as plt


# Schaffers
# Crowding random
# popSize = np.array([20, 30, 50], int)
# sigma = np.array([0.3, 0.5, 0.8, 1.0])
# selPressure = np.array([1.1, 1.3])
# eps = np.array([0.0, 0.01])
# Crowding seq
popSize = np.array([10, 20, 30, 50], int)
sigma = np.array([0.3, 0.5, 0.8, 1.0])
selPressure = np.array([1.1, 1.6])
eps = np.array([0.0, 0.01, 0.1])
dir = "out/Schaffers/cr/"

# Katsuura
# popSize = np.array([200, 300, 400, 500], int)
# sigma = np.array([0.0001, 0.0005, 0.001], float)
# selPressure = np.array([1.85, 1.95])
# eps = np.array(['0.00001', '0.00005'])
# dir = "out/Katsuura/crowding/"


popFit = []
simltnSize = 30

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
