#!/bin/bash

mkdir -p ./out

# compile
javac -sourcepath ./src -d ./out src/by/pantosha/itirod/lab1/Lab1.java

# run
java -classpath ./out/ by.pantosha.itirod.lab1.Lab1
