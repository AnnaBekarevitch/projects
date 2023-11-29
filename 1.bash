#!/bin/bash
bash mem.bash &
bash mem2.bash &
> "test1out"
while true; do
data=$(top -b -n 1)
echo "$data" | head -n 15 >> "test1out"
echo "$data" | grep "mem.bash" >> "test1out"
echo "$data" | grep "mem2.bash" >> "test1out"
done
dmesg | grep "mem[2]*.bash" > "test1log"
cat "report.log" | tail -n 1 >> "test1out"
cat "report2.log" | tail -n 1 >> "test1out"
