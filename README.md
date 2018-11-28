# Transitive
Track dependencies and know what's in the box.

### Create Dependency
> curl -X POST 'http://127.0.0.1:8080/?name=Customer%20Deliverable&version=1.0.0'

### Create Dependency with Parent
by parent id
> curl -X POST 'http://127.0.0.1:8080/1?name=Apache%20Commons%20Collections&version=4.2'

by parent name & version
> curl -X POST 'http://127.0.0.1:8080/?name=Docker&version=17.12.1-ce&parentName=Customer%20Deliverable&parentVersion=1.0.0'

### Load Dependency
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