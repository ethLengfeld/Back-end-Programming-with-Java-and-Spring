# Mediscreen

## Cassandra
1. To start cassandra now and restart at login:
   brew services start cassandra
   Or, if you don't want/need a background service you can just run:
   /opt/homebrew/opt/cassandra/bin/cassandra -f
2. open cqlsh
3. created keyspace
    1. CREATE KEYSPACE IF NOT EXISTS mediscreen_local WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
