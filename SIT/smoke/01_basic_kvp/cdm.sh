#!/bin/bash -e

cat <<EOF
!!!!!!!!
!!!!!!!!  Testing Migrate
!!!!!!!!
EOF

/local/cdm.sh -c 
spark-submit \
  --properties-file /smoke/01_basic_kvp/migrate.properties \
  --master "local[*]" \
  --class datastax.cdm.job.Migrate /local/cassandra-data-migrator.jar

cat <<EOF
!!!!!!!!
!!!!!!!!  Testing DiffData
!!!!!!!!
EOF

spark-submit \
  --properties-file /smoke/01_basic_kvp/migrate.properties \
  --master "local[*]" \
  --class datastax.cdm.job.DiffData /local/cassandra-data-migrator.jar
