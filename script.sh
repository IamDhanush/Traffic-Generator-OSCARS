#!/bin/bash

mvn -f ./oscar-trafficgenerator clean
mvn -f ./oscar-trafficgenerator -DskipTests package

declare -i numPaths=1
declare -i minnumflows=1
declare -i maxnumflows=1
declare -i holding=800
declare -i deviceproperties=3
declare -i bandwidth=1000
declare -i bandwidthdeviation=400
declare -i failure=1

for totalrequest in 5000
do
	    for arrival in 8 4 2.66 2 1.6
	    do
        	for seed in 101 102 103 
        	do
    	    	  	./oscars-newtech/bin/start.sh &
        	  	 sleep 21
        	  	 mvn -f ./oscar-trafficgenerator exec:java -Dexec.mainClass="com.acnl.oscartrafficgenerator.MainApp" -Dexec.args="$seed $totalrequest PALINDROME $numPaths $minnumflows $maxnumflows $arrival $holding $deviceproperties $bandwidth $bandwidthdeviation $failure oscarTrafficController NONE"
        	   	 pkill -9 -f target/pss-1.0.0-beta.jar
        	         #killall -9 java
        	 done
    	    done
done 






