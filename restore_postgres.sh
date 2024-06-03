#!/bin/bash

# Variables
DB_NAME="DB_NAME"
DB_USER="DB_USER"
PARENT_DIR="db_backups"
BACKUP_FILE="$PARENT_DIR/backup.sql"

# Set Password environment variable
export PGPASSWORD="DB_PASSWORD"

# Restore the backup
psql -U $DB_USER -d $DB_NAME -f "$BACKUP_FILE"
