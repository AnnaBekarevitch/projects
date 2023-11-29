#!/bin/bash 
> "test0out"
bash mem.bash &
while true; do
data=$(top -b -n 1)
echo "$data" | head -n 15 >> "test0out"
echo "$data" | grep "mem.bash" >> "test0out"
sleep 1s
done
