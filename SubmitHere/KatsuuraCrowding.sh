#!/usr/bin/env bash

for popSize in 2000
do
    for sigma in 0.01
    do
        for selPressure in 1.85
        do
            for eps in 0.000
            do
                for j in {1..100}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Katsuura/crowding/final/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=KatsuuraEvaluation \
                     -seed=2
                     echo j
                done

            done
         done
    done
done