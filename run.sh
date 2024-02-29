#!/bin/bash
#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=gateway-server
APP_VERSION=1.0.0



LOG_PATH=out.log

RED="\033[31m"
GREEN="\033[32m"
YELLOW="\033[33m"
END="\033[0m"

#使用说明，用来提示输入参数
usage() {
    echo "$YELLOW"
    echo "使用说明: sh run.sh [start|stop|restart|status|logs]"
    echo "  start	--启动"
    echo "   stop	--停止"
    echo "restart	--重启"
    echo " status	--状态"
    echo "   logs	--日志"
    echo "$END"
    exit 1
}

#检查程序是否在运行
is_exist() {
    pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}' `
    #如果存在返回1，不存在返回0
    if [ -z "${pid}" ]; then
      return 0
    else
      return 1
    fi
}

#启动方法
start() {
   is_exist
   if [ $? -eq "1" ]; then
     echo "$RED"
     echo "$APP_NAME 正在运行. 进程ID:$pid。"
	 echo "$END"
   else
     nohup java -jar "$APP_NAME-$APP_VERSION.jar" > $LOG_PATH 2>&1 &
	 pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`
	 echo "$GREEN"
	 echo "服务 $APP_NAME 开始启动！进程ID:$pid"
	 echo "........................启动成功！........................"
	 echo "$END"
   fi
}

#停止方法
stop() {
   is_exist
   if [ $? -eq "1" ]; then
     kill -9 $pid
	 echo "$GREEN"
	 echo "服务 $APP_NAME 已成功停止！进程ID:${pid} 已经被强制结束!"
	 echo "$END"
	 echo "" > $LOG_PATH
   else
	 echo "$RED"
	 echo "服务 $APP_NAME 没有运行! 无需关闭!"
	 echo "$END"
   fi
}

#输出运行状态
status() {
   is_exist
   if [ $? -eq "1" ]; then
     echo "$GREEN"
     echo "服务 $APP_NAME 正在运行。 进程ID: $pid"
	 echo "$END"
   else
     echo "$RED"
     echo "服务 $APP_NAME 没有运行。"
	 echo "$END"
   fi
}

#重启
restart() {
   echo "$YELLOW"
   echo "......................正在重启请稍候......................"
   echo "..........................................................$END"
   stop
   sleep 1;
   start
   echo "$GREEN........................重启成功！........................"
   echo "$END"
}

# 查看日志
logs(){
	  # 输出实时日志
	 tail -n 100 -f $LOG_PATH
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
   "start")
     start
     ;;
   "stop")
     stop
     ;;
   "status")
     status
     ;;
   "restart")
     restart
     ;;
   "logs")
	 logs
	 ;;
   *)
     usage
     ;;
 esac

 exit 0
