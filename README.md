# Transitive
You've built microservices, packaged them in containers and put them into 
production in customer environments. After several releases, a few updates 
and some bug fixes, you learn that Apache Commons Collections has a remote 
code execution vulnerability in both 3.0 - 4.0 versions. Without scanning each 
source repository and digging through version control history, you don't know 
which services are vulnerable or if you have a customer running an impacted 
version.

The goal of this project to easily answer this and similar questions.
 * What releases contain version **n** of library **y**?
 * What is in version **n** of the product release?

### Create Dependency
> curl -X POST 'http://127.0.0.1:8080/?name=Customer%20Deliverable&version=1.0.0'

### Create Dependency with Parent
by parent id
> curl -X POST 'http://127.0.0.1:8080/1?name=Apache%20Commons%20Collections&version=4.2'

by parent name & version
> curl -X POST 'http://127.0.0.1:8080/?name=Docker&version=17.12.1-ce&parentName=Customer%20Deliverable&parentVersion=1.0.0'

### Load Dependency and Child Dependencies
by id
> curl -X GET http://127.0.0.1:8080/1

by name & version
>curl -X GET 'http://127.0.0.1:8080/?name=Customer%20Deliverable&version=1.0.0'

```
{
     "id": 1,
     "name": "Customer Deliverable",
     "version": "1.0.0",
     "childDependencies": [
         {
             "id": 3,
             "name": "Apache Commons Collections",
             "version": "4.2",
             "childDependencies": []
         },
         {
             "id": 5,
             "name": "Docker",
             "version": "17.12.1-ce",
             "childDependencies": []
         }
     ]
 }
 ```
 
 ### Load Dependency and Child Dependencies as Tree
 by id
 > curl -X GET http://127.0.0.1:8080/tree/1
 
 by name & version
 >curl -X GET 'http://127.0.0.1:8080/tree/?name=Customer%20Deliverable&version=1.0.0'

```
Customer Deliverable 1.0.0
  +-\ Apache Commons Collections 4.2
  +-\ Docker 17.12.1-ce
```

 
 ### Export Dependency and Child Dependencies Tree
 by id
 > curl -X GET http://127.0.0.1:8080/tree/export/1
 
 by name & version
 >curl -X GET 'http://127.0.0.1:8080/tree/export/?name=Customer%20Deliverable&version=1.0.0'
