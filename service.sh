jar_name="$2"

start() {
  echo "service start..."
  nohup java -jar -Dloader.path=./libs "$jar_name" >./output.log 2>&1 &
}

stop() {
  echo "service stop..."
  pid=$(getPid)
  kill "$pid"
}

restart() {
  stop
  start
}

getPid() {
  pid=$(ps -ef|grep "$jar_name"|grep -v grep|awk '{printf $2}')
  echo "$pid"
}

status() {
  pid=$(getPid)
  if [ "$pid" == "" ]; then
    echo "service is stopped"
  else
    echo "service is starting, pid is $pid"
  fi
}

case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  status)
    status
    ;;
  *)
    echo "start | stop | restart | status"
    ;;
esac