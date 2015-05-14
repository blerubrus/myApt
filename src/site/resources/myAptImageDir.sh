#!/bin/sh
# This script is a shortcut to invoke the myApt jar file expected in the bin directory with options to generate a set of "images" apt file
# Expected parameter: the path to a directory containing images
# Usage example: "myAptImageDir src/site/resources/images/"

# eventual first option may be a request to display the version of the program
VERSION=""

# checks if the first parameter is a request for the version
case $1 in -v | -version | --version )
   VERSION="-v"
   ;;
esac

java -jar ~/bin/myApt.jar -DimageDir=$1 $VERSION
