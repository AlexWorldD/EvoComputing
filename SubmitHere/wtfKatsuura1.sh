#!/usr/bin/env bash

#for popSize in 500 2000
#do
#    for sigma in 0.008 0.01 0.012
#    do
#        for selPressure in 1.8 1.85 1.95
#        do
#            for eps in 0.01 0.001
#            do
#                for j in {1..10}
#                do
#                    java -DpopSize=$popSize -Dmethod="crowding" -Dsigma=$sigma -DupdSize=1 -DselectionPressure=$selPressure -Deps=$eps \
#                    -jar testrun.jar > "./out/Katsuura/crowding/veryBigPop/Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps"_"$j".txt" \
#                     -submission=player52 \
#                     -evaluation=KatsuuraEvaluation \
#                     -seed=2
#                done
#                echo "Size_"$popSize"_Sigma_"$sigma"_SelPressure_"$selPressure"_Eps_"$eps
#            done
#         done
#    done
#done
for popSize in 30 50 80 100
do
    for sigma in 0.0001 0.0005 0.001
    do
        for selPressure in 1.85 1.95
        do
            for eps in 0.01 0.001
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