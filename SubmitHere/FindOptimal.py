import numpy as np
import matplotlib.pyplot as plt
import os

# BentCigar
popSize = np.array([40, 60, 80], int)
sigma = np.array([0.8, 1.0])
selPressure = np.array([1.2, 1.6, 1.9])
eps = np.array([0.01, 0.1])
dir = "out/BentCigar/crowding/lex/"
# Schaffers
# Crowding random
# popSize = np.array([20, 30, 50], int)
# sigma = np.array([0.3, 0.5, 0.8, 1.0])
# selPressure = np.array([1.1, 1.3])
# eps = np.array([0.0, 0.01])
# Baseline
# popSize = np.array([40, 50, 60, 100, 120, 160], int)
# sigma = np.array([0.7, 0.8, 0.9])
# selPressure = np.array([1.7, 1.8, 1.9, 1.95, 2.0])
# popSize = np.array([100, 120], int)
# sigma = np.array([0.9, 0.95, 1.0, 0.96, 0.97])
# selPressure = np.array([1.7, 1.8, 1.9, 1.95])
# eps = np.array(['0.000', '0.00001'])
# dir = "out/Schaffers/baseline/lex2/"

# Katsuura low size
# popSize = np.array([30, 50, 80, 100], int)
# sigma = np.array([0.0001, 0.0005, 0.001], float)
# selPressure = np.array([1.85, 1.95])
# eps = np.array(['0.00001', '0.00005', '0.01', '0.001'])
# dir = "out/Katsuura/crowding/"

# # Katsuura Big size
# popSize = np.array([140, 200, 250, 300, 400, 500], int)
# sigma = np.array([0.001, 0.01, 0.1], float)
# selPressure = np.array([1.85, 1.95])
# eps = np.array(['0.000001', '0.000'])
# dir = "out/Katsuura/crowding/bigPop/"

# Katsuura very big size
# popSize = np.array([500, 1000, 1500, 2000], int)
# sigma = np.array([0.008, 0.01, 0.012], float)
# selPressure = np.array([1.8, 1.85, 1.95])
# eps = np.array(['0.000001', '0.000'])
# dir = "out/Katsuura/crowding/veryBigPop/"


popFit = []
simltnSize = 10
delta = 0

format = ".txt"
combinations = [(psize, s, sp, _eps) for _eps in eps for psize in popSize for s in sigma for sp in selPressure]
print(combinations)
score = dict()
for psize, s, sp, _eps in combinations:
    som = 0
    delta = 0
    for j in range(1, simltnSize + 1):
        dirctry = dir + "Size_" + str(psize) + "_Sigma_" + str(s) + "_SelPressure_" + str(sp) + "_Eps_" +str(_eps)+"_" + str(j) + format
        lines = np.loadtxt(dirctry, comments="R", delimiter=" ", unpack=False, usecols=1)
        if lines.size==1:
            som += lines
        elif lines.size==0:
            delta+=1
    score[(psize, s, sp, _eps)] = som / (simltnSize-delta)

score = sorted(score.items(), key=lambda x: x[1])
print(max(score, key=lambda key: score[key]))


#  ////////Katsuura only mutation
# popSize = np.array([40, 60, 80, 120], int)
# sigma = np.array([0.005], float)
# selPressure = np.array([1.8, 1.95])
# eps = np.array(['0.0002', '0.0004'])
# epsMax = np.array([2.0, 3.0, 4.0], float)
# dir = "out/Katsuura/crowding/onlyMut/"

# popFit = []
# simltnSize = 5
# delta = 0
#
# format = ".txt"
# combinations = [(psize, s, sp, _eps, _epsM) for _epsM in epsMax for _eps in eps for psize in popSize for s in sigma for
#                 sp in selPressure]
# print(combinations)
# score = dict()
# for psize, s, sp, _eps, _epsM in combinations:
#     som = 0
#     delta = 0
#     for j in range(1, simltnSize + 1):
#         dirctry = dir + "Size_" + str(psize) + "_Sigma_" + str(s) + "_SelPressure_" + str(sp) + "_Eps_" + str(
#             _eps) + "_EpsMax_" + str(_epsM) + "_" + str(j) + format
#         lines = np.loadtxt(dirctry, comments="R", delimiter=" ", unpack=False, usecols=1)
#         if lines.size == 1:
#             som += lines
#         elif lines.size == 0:
#             delta += 1
#     score[(psize, s, sp, _eps, _epsM)] = som / (simltnSize - delta)
#
# score = sorted(score.items(), key=lambda x: x[1])
# print(max(score, key=lambda key: score[key]))
