#!/usr/bin/env bash

for popSize in 40 60 80
do
    for sigma in  0.8 1.0
    do
        for selPressure in 1.2 1.6 1.9
        do
            for eps in 0.01 0.1
            do
                for j in {1..10}
                do
                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
                    -jar testrun.jar > "./out/BentCigar/crowding/lex/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
                     -submission=player52 \
                     -evaluation=BentCigarFunction \
                     -seed=2
                done
                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
            done
         done
    done
done