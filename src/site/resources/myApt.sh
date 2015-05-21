#!/bin/sh
# This script is a shortcut to generate an apt file on invoking the myApt.jar executable jar file expected in the ~/bin/ directory of the current user
# This script is configured to handle default values for 3 possible input arguments:
# $1: the filename of the apt file to generate. Default is "src/site/apt/yyyyMMdd_myApt.apt" (with the current date)
# $2: the title of the document (default: "yyyy-MM-dd myApt")
# $3: "svn" optional option to inject svn Id URL Date Author Revision rcs auto-completed keywords in the generated file (header and footer)
# Note: the "-Dtoc" argument is passed to myApt.jar to include the Doxia Macro TOC instruction in the apt file.
# Note: typing "-v" or "-version" or "--version" as single argument will display the jar version.


# user input as first parameter is the filename and path of the apt file to generate (or version) 
FILENAME=$1

# user input as second parameter is the title of the document to generate
TITLE=$2

# eventual third option to inject rcs keywords in the apt
SCM=$3

# appends the "-Dscm=" with the eventual third user input (overwritten to an empty string if the third option is not given)
SCM_OPTION="-Dscm="$3

# maybe the user just asks to display the version
VERSION=""

# current system date in the yyyy-MM-dd format (if no title is given) for the title
TODAY=$(date +"%Y-%m-%d")

# current system date in the yyyyMMdd format (if no filename is given) for the filename
TODAY_ATTACHED=$(date +"%Y%m%d")

# checks user input first parameter (filename), if empty, sets a default value
if [ -z "$FILENAME" ]
then
   FILENAME=$TODAY_ATTACHED"_myApt.apt"
fi

# checks user input second parameter (title), if empty, sets a default value
if [ -z "$TITLE" ]
then
   TITLE=$TODAY" myApt"
fi

# checks user input third parameter (svn), if empty, sets an empty string for the third parameter
if [ -z "$SCM" ]
then
   SCM_OPTION=""
fi

# checks if the first parameter (value of FILENAME) is a request for the version
case $FILENAME in -v | -version | --version )
   VERSION="-v"
   ;;
esac


# invokes the myApt jar file (expected in the bin directory) with given parameters
java -jar ~/bin/myApt.jar -Dtarget=$FILENAME -Dtitle="$TITLE" $SCM_OPTION -Dtoc $VERSION
