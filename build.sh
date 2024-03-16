docker easylearing-c | true
docker rmi easylearing-i | true

until docker build -t easylearing-i .
do
    echo "Waiting for build image easylearing-i ..."
    sleep 4
done

docker run -d -p 8080:8080 --name easylearing-c easylearing-i