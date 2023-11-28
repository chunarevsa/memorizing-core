#!/bin/bash

# Имя тома Docker
VOLUME_NAME=memorizing-core-card-service-db-data

# Имя файла резервной копии
BACKUP_FILE=backup-$(date +"%Y%m%d%H%M%S").tar.gz

# Создание резервной копии данных в архиве
docker run --rm -v $VOLUME_NAME:/data -v $(pwd):/backup alpine tar czf /backup/$BACKUP_FILE -C /data .

echo "Backup completed: $BACKUP_FILE"
