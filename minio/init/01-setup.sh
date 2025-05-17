#!/bin/bash

set -e

until mc alias set minio-local http://minio:9000 "$MINIO_ROOT_USER" "$MINIO_ROOT_PASSWORD" > /dev/null 2>&1; do
    echo "Ожидание MinIO..."
    sleep 1
done

if ! mc stat minio-local/images > /dev/null 2>&1; then
    mc mb minio-local/images
    echo "Бакет 'images' создан"
fi

KEYS=$(mc admin user svcacct add minio-local "$MINIO_ROOT_USER" --access-key "app-key" --secret-key "app-secret" --policy "readwrite")

ACCESS_KEY=$(echo "$KEYS" | grep "Access Key:" | awk '{print $3}')
SECRET_KEY=$(echo "$KEYS" | grep "Secret Key:" | awk '{print $3}')

echo "MINIO_ACCESS_KEY=$ACCESS_KEY" >> .env
echo "MINIO_SECRET_KEY=$SECRET_KEY" >> .env

echo "MinIO инициализирован. Ключи сохранены в .env"