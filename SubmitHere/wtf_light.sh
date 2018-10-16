#!/usr/bin/env bash

for popSize in 20 40
do
    for sigma in 0.01 0.05
    do
        for selPressure in 1.0 1.5
        do
            for eps in 0.0 0.01 0.1
            do
                for j in {1..2}
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