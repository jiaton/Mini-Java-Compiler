#!/bin/bash
fileName=$1

java TypeCheck.Typecheck < ${fileName}.java
java TypeCheck.Typecheck < ${fileName}-error.java
java J2V.J2V < ${fileName}.java > ${fileName}.vapor
java -cp RegisterAllocation\vapor-parser.jar;. RegisterAllocation.V2VM < ${fileName}.vapor > ${fileName}.vaporm
java -cp VM2M\dependencies\vapor-parser.jar;. VM2M.VM2M < ${fileName}.vaporm > ${fileName}.s
java -jar VM2M\dependencies\mars.jar nc ${fileName}.s