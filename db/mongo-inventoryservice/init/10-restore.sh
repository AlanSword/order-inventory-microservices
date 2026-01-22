#!/usr/bin/env bash
set -e

mongorestore --gzip --archive=/docker-entrypoint-initdb.d/inventoryservice.archive.gz --db "$MONGO_INITDB_DATABASE"
