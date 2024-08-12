#!/bin/sh
# Monitor memory usage `apt install sysstat`
pidstat --human -r -p $(ps aux | grep AppKt | awk '{print $2}' | head -n1) 5
