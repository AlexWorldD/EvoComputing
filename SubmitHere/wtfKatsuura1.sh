#!/usr/bin/env bash

for popSize in 500 2000
do
    for sigma in 0.008 0.01 0.012
    do
        for selPressure in 1.8 1.85
        do
            for eps in 0.000001 0.000
            do
                for j in {1..10}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Katsuura/crowding/veryBigPop/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=KatsuuraEvaluation \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done