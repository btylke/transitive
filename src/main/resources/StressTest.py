import requests
import time

total = 0

# create a top level parent
requests.post('http://127.0.0.1:8080/?name=parent&version=0')

for i in xrange(1000):
    start = time.time()
    for n in xrange(1000):
        print "Creating child: ", total
        requests.post("http://127.0.0.1:8080/?name=child&version=%d&parentName=parent&parentVersion=0" % total)
        total += 1

    print "Added 1000 Children in: " + str(time.time() - start)

start = time.time()
requests.get("http://127.0.0.1:8080/?name=parent&version=0")
print "Time to load a parent with 1,000,000 children: " + str(time.time() - start)