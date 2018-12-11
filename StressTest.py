#!/usr/bin/python3

import requests
import time
import random
from multiprocessing.pool import Pool

pool = Pool(10)

def createDependency(id):
	print ("Creating child: ", id)
	requests.post("http://127.0.0.1:8080/?name=child&version=%d&parentName=parent&parentVersion=0" % id)

def createRandomParentDependency(id):
	parent_id = random.randint(1, 100)
	print ("Creating child: ", id, "of parent", parent_id)
	requests.post("http://127.0.0.1:8080/%d?name=child&version=%d" % (parent_id,id))

def main():
	# create a top level parent
	requests.post('http://127.0.0.1:8080/?name=parent&version=0')

	# create the first 100 children
	for i in range(0, 99):
		createDependency(i)

	# create a few more with random parents
	pool.map(createRandomParentDependency, range(100, 1000000))
	pool.close() 
	pool.join() 
		
	start = time.time()
	requests.get("http://127.0.0.1:8080/?name=parent&version=0")
	print ("Time to load a parent with 1,000,000 children: " + str(time.time() - start))

if __name__ == '__main__':
    main()