# Evolutionary Computing, Group 52
<hr>

### Dependensies:
  - [Maven](https://maven.apache.org/)
  - A lot of luck :)
### How to use?:
  00. Clone repo to you machine
  `git clone https://github.com/AlexWorldD/EvoComputing/`
  0. Open project in [IntelliJ](https://www.jetbrains.com/idea/) as a Maven project.
  1. Add libraries: `org.vu.contest` and `org.vu.testrun` to File->Project Structure->Libraries
  2. **Ubuntu Special**, also here add `cjavabbob.dll` as a library
  3. Build Project
  4. Develop and test your parameters/methods
  
### How to set parameters?
0. Copy `player52.java` and `model` to *SubmitHere* folder
1. Compile all code: \
`javac -cp contest.jar *.java model/*.java \
 && jar cmf MainClass.txt submission.jar *.class model/*.class`
 
2. Use `-D<ParameterName>=<parameterValue>`, \
for instance `-DselectionPressure=1.8`
### How to make Submission.jar with Maven?
  0. Rebuild Project
  1. In project directory writhe `mvn package`
  2. Copy submission.jar from `target` folder to the root
  3. Test it locally \
  `java -jar testrun.jar -submission=player52 -evaluation=BentCigarFunction -seed=1`
  4. Submit `submission.jar` to server
  
