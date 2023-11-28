# PowerShell-скрипт для импорта данных из архива в новый том Docker

# Имя нового тома Docker
$NewVolumeName = "memorizing-core_card-service-db-data"

# Имя файла резервной копии
$BackupFile = "backup-20231122201110.tar.gz"

# Создание нового тома
docker volume create $NewVolumeName

# Импорт данных из архива в новый том
docker run --rm -v "${NewVolumeName}:/data" -v "$(pwd):/backup" alpine sh -c "tar xzf /backup/${BackupFile} -C /data"

Write-Host "Import completed to ${NewVolumeName}"
