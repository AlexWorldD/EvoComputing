var1='i'
for popSize in 50 60
do
    for j in {1..4}
    do
        java -DpopSize=$popSize -Dmethod="baseline" -DupdSize=1 -DselectionPressure=1.8 -jar testrun.jar > "./out/BentCigar/storeresults$popSize$var1$j.txt" -submission=player52 -evaluation=BentCigarFunction -seed=2
    done
done