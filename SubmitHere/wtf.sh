#!/usr/bin/env bash

for popSize in 20 30 40 50 60 100 130 150 160 180 200
do
    for sigma in 0.001 0.005 0.01 0.05 0.15 0.3 0.5 0.8
    do
        for selPressure in 1.0 1.5 1.8 2.0
        do
            for eps in 0.0 0.001 0.01 0.1
            do
                for j in {1..30}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/BentCigar/crowding/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=BentCigarFunction \
                     -seed=2
                done
            done
         done
    done
done