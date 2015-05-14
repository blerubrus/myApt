#!/bin/sh
# This script is a shortcut to generate an apt "resources" file (links to existing files in apt format) by invoking the myApt jar file expected in the bin directory.
# Expected parameter: the relative path to the directory to browse (default: src/site/resources/)
# Usage example: "myAptResources src/site/resources/images"
# To add svn rcs keyword (Id Date URL Author Revision) in the apt file, add the "svn" as a second option
# Example: "./myAptResources.sh src/site/resources svn"
# To display the version: "./myAptResources.sh -v"

# first argument is the path of the directory to browse by the program
DIR_TO_BROWSE=$1

# maybe the user just asks to display the version
VERSION=""

# checks user input first parameter (dir to browse), if empty, sets a default value
if [ -z "$DIR_TO_BROWSE" ]
then
   DIR_TO_BROWSE="src/site/resources"
fi

# eventual second option to inject rcs keywords in the apt
SCM=$2

# appends the "-Dscm=" with the eventual third user input (overwritten to an empty string if the third option is not given)
SCM_OPTION="-Dscm="$2

# checks user input second parameter (svn), if empty, sets an empty string for the third parameter
if [ -z "$SCM" ]
then
   SCM_OPTION=""
fi

# checks if the first parameter (value of DIR_TO_BROWSE) is a request for the version
case $DIR_TO_BROWSE in -v | -version | --version )
   VERSION="-v"
   ;;
esac


# launches the command
java -jar ~/bin/myApt.jar -Dtype=resources -DresourcesDir=$DIR_TO_BROWSE $SCM_OPTION $VERSION
