# How to test repo ?

## in short:
In project root invoke:
```
./dev-network.sh reset && sleep 10
( cd producer-car-tracks && gradle run ) && sleep 10
( cd consumer-car-tracks-db && gradle run ) && sleep 10
```


### Step 1
`docker ps` should give output similar to:  
```
af1597c6537f   kafka-fundamentals_kafka1_1      Up 5 minutes (healthy)
00f6e1f94219   kafka-fundamentals_kafka2_1      Up 5 minutes (healthy)
77452ecbdc73   kafka-fundamentals_tools_1       Up 5 minutes (healthy)
7c4e4ddda2e5   kafka-fundamentals_zookeeper_1   Up 5 minutes (healthy)
```

### Step 2
should have output similar to:  
```
> Task :producer-car-tracks:run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
sent record(key=null value=Hello! ==> 0) meta(partition=0, offset=0)
sent record(key=null value=Hello! ==> 1) meta(partition=0, offset=1)
sent record(key=null value=Hello! ==> 2) meta(partition=0, offset=2)
sent record(key=null value=Hello! ==> 3) meta(partition=0, offset=3)
sent record(key=null value=Hello! ==> 4) meta(partition=0, offset=4)
sent record(key=null value=Hello! ==> 5) meta(partition=0, offset=5)
sent record(key=null value=Hello! ==> 6) meta(partition=0, offset=6)
sent record(key=null value=Hello! ==> 7) meta(partition=0, offset=7)
sent record(key=null value=Hello! ==> 8) meta(partition=0, offset=8)
sent record(key=null value=Hello! ==> 9) meta(partition=0, offset=9)
```

### Step 3
should have output similar to:
```
> Task :consumer-car-tracks-db:run
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Received 0 records
Received 0 records
Received 0 records
Received 10 records
Consumer Record:(null, Hello! ==> 0, 0, 0)
Consumer Record:(null, Hello! ==> 1, 0, 1)
Consumer Record:(null, Hello! ==> 2, 0, 2)
Consumer Record:(null, Hello! ==> 3, 0, 3)
Consumer Record:(null, Hello! ==> 4, 0, 4)
Consumer Record:(null, Hello! ==> 5, 0, 5)
Consumer Record:(null, Hello! ==> 6, 0, 6)
Consumer Record:(null, Hello! ==> 7, 0, 7)
Consumer Record:(null, Hello! ==> 8, 0, 8)
Consumer Record:(null, Hello! ==> 9, 0, 9)
Received 0 records
Received 0 records
Received 0 records

```
