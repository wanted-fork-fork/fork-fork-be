#!/bin/bash

# 포트 번호를 입력받음
read -p "Enter the port number: " PORT

# 해당 포트를 사용하는 프로세스를 찾음
PID=$(lsof -i :$PORT -t)

# 프로세스가 존재하는지 확인
if [ -z "$PID" ]; then
    echo "No process is using port $PORT"
else
    echo "Process using port $PORT: $PID"

    # 프로세스를 종료할지 물어봄
    read -p "Do you want to kill this process? (y/n): " CONFIRM
    if [ "$CONFIRM" == "y" ]; then
        kill -9 $PID
        echo "Process $PID killed"
    else
        echo "Process not killed"
    fi
fi
