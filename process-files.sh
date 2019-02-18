#!/bin/bash

# validate arguments
if [[ $# < 3 ]]
then
    echo "usage: $0 CLASS OUTPUT_DIR FILES..."
fi

# determine convert.sh script location
BASEDIR=$(dirname $0)
CONVERT="$BASEDIR/convert.sh"

# create output directory
mkdir -p $2

# iterate over files
for FILEPATH in "${@:3}"
do
    echo "$(date): '$FILEPATH'"

    FILENAME=$(basename "$FILEPATH")
    BASENAME="${FILENAME%.*}"

    $CONVERT $1 $FILEPATH "$2/$BASENAME.bin"
    #echo "    $2/$BASENAME.bin"
done
