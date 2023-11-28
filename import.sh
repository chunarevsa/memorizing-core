#!/bin/bash

# Имя нового тома Docker
NEW_VOLUME_NAME="memorizing-core-card-service-db-data"

# Имя файла резервной копии
BACKUP_FILE="backup.tar.gz"

# Создание нового тома
docker volume create $NEW_VOLUME_NAME

# Импорт данных из архива в новый том
docker run --rm -v $NEW_VOLUME_NAME:/data -v $(pwd):/backup alpine sh -c "tar xzf /backup/$BACKUP_FILE -C /data"

echo "Import completed to $NEW_VOLUME_NAME"
