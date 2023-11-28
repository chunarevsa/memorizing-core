# PowerShell-скрипт для создания резервной копии данных тома Docker

# Имя тома Docker
$VolumeName = "memorizing-core_card-service-db-data"

# Имя файла резервной копии
$BackupFile = "backup-$(Get-Date -Format 'yyyyMMddHHmmss').tar.gz"

# Создание резервной копии данных в архиве
docker run --rm -v "${VolumeName}:/data" -v "$(pwd):/backup" alpine tar czf "/backup/${BackupFile}" -C /data .

Write-Host "Backup completed: ${BackupFile}"
