#!/usr/bin/env bash

for popSize in 400 500
do
    for sigma in 0.0001 0.0005 0.001
    do
        for selPressure in 1.85 1.95
        do
            for eps in 0.00001 0.00005
            do
                for j in {1..5}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Katsuura/crowding/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=KatsuuraEvaluation \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done