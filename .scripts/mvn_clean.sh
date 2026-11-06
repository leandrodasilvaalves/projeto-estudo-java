#!/bin/bash

echo Removendo marcadores de falha de download do Maven
find ~/.m2/repository -name "*.lastUpdated" -type f -delete

echo "Removendo marcadores de falha específicos do m2e (Eclipse/JDT.LS) — nome diferente!"
find ~/.m2/repository -name "m2e-lastUpdated.properties" -type f -delete

echo "Removendo carimbos de origem com hash que travam o modo offline (a causa final)"
find ~/.m2/repository -name "_remote.repositories" -type f -delete

echo Confirmando que não sobrou nenhum .lastUpdated
find ~/.m2/repository -name "*.lastUpdated" -type f | wc -l   # -> 0

echo Resolvendo dependencias
mvn  dependency:resolve