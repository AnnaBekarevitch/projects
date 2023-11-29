#!/bin/bash
a={}
x=0
> newreport.log
while true; do
a+=(1 2 3 4 5 6 7 8 9 10)
x=$(($x + 10))
if ! (( x % 1000000 )); then 
echo "$x" >> newreport.log
fi
if [[ $x -ge $1 ]]; then
break
fi
done 
