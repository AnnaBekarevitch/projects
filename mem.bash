#!/bin/bash
arr={}
x=0
> report.log
while true; do
arr+=(1 2 3 4 5 6 7 8 9 10)
x=$(($x + 10))
if !((x % 1000000 )); then
echo "$x" >> report.log
fi
done
