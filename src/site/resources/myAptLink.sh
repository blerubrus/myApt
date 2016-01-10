#!/bin/sh
# This script is a shortcut to generate the apt code for links to file or directory given as argument by invoking the myApt jar file expected in the bin directory.
# Expected parameter: the relative path to the directory to browse (default: src/site/resources/) or file
# Usage example: "myAptLink src/site/resources/images"
# For a single file given as parameter, a second parameter may be passed for the link label. Example: "./myAptLink.sh src/site/resources/myFile myLabel"
# To display the version: "./myAptLink.sh -v"

# first argument is the path of the directory or file to browse by the program
LINK_TO=$1

# maybe the user just asks to display the version
VERSION=""

# checks user input first parameter (dir to browse), if empty, sets a default value
if [ -z "$LINK_TO" ]
then
   LINK_TO="src/site/resources"
fi

# eventual second option for a label (only with a file target)
LINK_LABEL=$2

# appends the "-Dscm=" with the eventual third user input (overwritten to an empty string if the third option is not given)
LINK_LABEL_OPTION="-Dlinklabel="$2

# checks user input second parameter (svn), if empty, sets an empty string for the third parameter
if [ -z "$LINK_LABEL" ]
then
   LINK_LABEL_OPTION=""
fi

# checks if the first parameter (value of LINK_TO) is a request for the version
case $LINK_TO in -v | -version | --version )
   VERSION="-v"
   ;;
esac


# launches the command
java -jar ~/bin/myApt.jar -Dlinkto=$LINK_TO $LINK_LABEL_OPTION $VERSION
