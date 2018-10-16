#!/usr/bin/env bash

for popSize in 20 50 80 150
do
    for sigma in 0.001 0.05 0.15 0.3 0.5 0.8
    do
        for selPressure in 1.0 1.8
        do
            for eps in 0.0 0.01 0.1
            do
                for j in {1..5}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Schaffers/crowding/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=SchaffersEvaluation \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done