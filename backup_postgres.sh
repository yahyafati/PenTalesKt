#!/bin/bash

# Variables
DB_NAME="DB_NAME"
DB_USER="DB_USER"
BACKUP_FILE="db_backups/backup_$(date +%Y%m%d%H%M%S).sql"

# Create the backup directory
mkdir -p db_backups

# Perform the backup
pg_dump -U $DB_USER -F c -f "$BACKUP_FILE" $DB_NAME

