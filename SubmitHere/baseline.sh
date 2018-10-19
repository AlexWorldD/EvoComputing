#!/usr/bin/env bash

for popSize in 40 50 60 100 120 160
do
    for sigma in  0.95 1.0
    do
        for selPressure in 1.95 2.0
        do
            for eps in 0.000 0.00001
            do
                for j in {1..10}
                do
                    java -DpopSize=$popSize -Dmethod="baseline" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/Schaffers/baseline/lex/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=SchaffersEvaluation \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done