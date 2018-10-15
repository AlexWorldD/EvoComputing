#!/usr/bin/env bash
for popSize in 20 40
do
    for j in {1..4}
    do
        java -DpopSize=$popSize -Dmethod="crowding" -DupdSize=1 -DselectionPressure=1.8 \
        -jar testrun.jar > "./out/BentCigar/crowding/Size_$popSize_$j.txt" \
         -submission=player52 \
         -evaluation=BentCigarFunction \
         -seed=2
    done
done