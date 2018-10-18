#!/usr/bin/env bash

for popSize in 40 60
do
    for epsMax in 2.0 3.0 4.0
    do
        for sigma in 0.005
        do
            for selPressure in 1.8 1.95
            do
                for eps in 0.0002 0.0004
                do
                    for j in {1..5}
                    do
                        java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps -DepsMax=$epsMax \
                        -jar testrun.jar > "./out/Katsuura/crowding/onlyMut/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_EpsMax_"$epsMax"_"$j".txt" \
                         -submission=player52 \
                         -evaluation=KatsuuraEvaluation \
                         -seed=2
                    done
                    echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
                done
             done
        done
    done
done