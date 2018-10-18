#!/usr/bin/env bash

for popSize in 50
do
    for sigma in  0.8
    do
        for selPressure in 1.1
        do
            for eps in 0.0
            do
                for j in {1..100}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Schaffers/crowding/final/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=SchaffersEvaluation \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done