#!/usr/bin/env bash

for popSize in 200
do
    for sigma in  0.90 0.92 0.93
    do
        for selPressure in 1.9 1.88
        do
            for eps in 0.000
            do
                for epsMax in 1.0 2.0 3.0 4.0 5.0
                do
                    for j in {1..15}
                    do
                        java -DpopSize=$popSize -Dmethod="baseline" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                        -jar testrun.jar > "./out/contest/e/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_EpsMax_"$epsMax"_"$j".txt" \
                         -submission=player52 \
                         -evaluation=SchaffersEvaluation \
                         -seed=2
                    done
                    echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
                done
            done
         done
    done
done