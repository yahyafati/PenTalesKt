#!/bin/bash

# Variables
DB_NAME="reading_realm"
DB_USER="postgres"
PARENT_DIR="db_backups"
BACKUP_FILE="$PARENT_DIR/backup_$(date +%Y%m%d%H%M%S).sql"

# Set Password environment variable
export PGPASSWORD="DB_PASSWORD"

# Create the backup directory
mkdir -p PARENT_DIR

# Perform the backup
pg_dump -U $DB_USER -d $DB_NAME -f "$BACKUP_FILE"

