#!/bin/sh
# This script is a shortcut to generate an apt "month done" file by invoking the myApt jar file expected in the bin directory
# Uses the current year and month if none parameter is given.
# To generate a "month done" apt file for a particular month and year, type 2 parameters:
# $1: the year (example: "2004")
# $2: the month (example: "1" for January)
# Example: myAptMonthDone 2004 1
# To add svn rcs keywods (Id URL Author Revision Date) in the apt file, specify the year the month then add "svn" to the command
# Example: "./myAptMonthDone.sh 2004 1 svn"
# To display the version: "./myAptMonthDone.sh -v"

# eventual third option to inject rcs keywords in the apt
SCM=$3

# eventual first option may be a request to display the version of the program
VERSION=""

# appends the "-Dscm=" with the eventual third user input (overwritten to an empty string if the third option is not given)
SCM_OPTION="-Dscm="$3

# checks user input third parameter (svn), if empty, sets an empty string for the third parameter
if [ -z "$SCM" ]
then
   SCM_OPTION=""
fi

# checks if the first parameter is a request for the version
case $1 in -v | -version | --version )
   VERSION="-v"
   ;;
esac

# launches the command
java -jar ~/bin/myApt.jar -Dtype=done -Dyear=$1 -Dmonth=$2 $SCM_OPTION $VERSION
