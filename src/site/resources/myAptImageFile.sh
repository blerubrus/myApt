#!/bin/sh
# This script is a shortcut to invoke the myApt jar file expected in the bin directory with options to generate an apt file containing the code to show a link and an image
# Expected parameter: the path to an image file (ideally in the src/site/resources/ hierarchy of a Maven Project Web Site)
# Usage example: "myAptImageDir src/site/resources/images/"

# eventual first option may be a request to display the version of the program
VERSION=""

# checks if the first parameter is a request for the version
case $1 in -v | -version | --version )
   VERSION="-v"
   ;;
esac

java -jar ~/bin/myApt.jar -DimageFile=$1 $VERSION
