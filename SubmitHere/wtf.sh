#!/usr/bin/env bash

for popSize in 20 40
do
    for sigma in 0.01 0.05
    do
        for selPressure in 1.0 1.5 1.8 2.0
        do
            for j in {1..2}
            do
                java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure \
                -jar testrun.jar > "./out/BentCigar/crowding/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_"$j".txt" \
                 -submission=player52 \
                 -evaluation=BentCigarFunction \
                 -seed=2
            done
         done
    done
done