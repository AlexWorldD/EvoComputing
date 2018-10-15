#!/usr/bin/env bash
javac -cp contest.jar *.java model/*.java \
&& jar cmf MainClass.txt submission.jar *.class model/*.class