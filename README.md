1/ Download and install Elasticsearch 7
https://www.elastic.co/fr/downloads/past-releases#elasticsearch --> take Elasticsearch 7.4.1

The Java API is documented here :
Client : https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.4/index.html
Indexing (bulk API) : https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.4/java-docs-bulk.html
Indexing (bulk processor, if you
prefer) : https://www.elastic.co/guide/en/elasticsearch/client/java-api/7.4/java-docs-bulk-processor.html

2/ Initialize a *private* bitbucket git repository and share it with jobs@adelean.com

3/ Create a program that unzips then parses the xml file in xml.zip.
Although the xml file contains only a few documents, take the hypothesis that it can be huge, so don't load all the
documents in memory at once.

4/ Send the parsed data to Elasticsearch. One index should be created.

5/ Commit the different steps and the final result into the git repository.


---

# Requirements

- Java 21
- Maven 3.8.4
- Docker compose

## With Sdkman

```shell
sdk env
````

# How to run

## Start Elasticsearch

```shell
docker compose up -d
```

## Run the application

add a .env file by copying the .env.example file and fill the values

```shell
cp .env.example .env
```

```shell
mvn clean install
export $(cat .env | xargs)
java -jar target/kata-1.0-SNAPSHOT-jar-with-dependencies.jar
```

You can then check the data in Elasticsearch with the following command:

```shell
curl -X GET "localhost:9200/products/_search" -H 'Content-Type: application/json' -d'
{
  "query": {
    "match_all": {}
  }, "_source": true, "size" : 1000
}'
```

# Solution

- Adaptation des versions d'Elasticsearch pour la compatibilité avec les processeurs Apple M2
- Implémentation d'une architecture en couches simple et maintenable
- Utilisation de librairies standards pour faciliter la maintenance à long terme

# Pistes d'amélioration

## Gestion des identifiants uniques

Cette problématique nécessite une analyse approfondie du contexte métier :

- Nécessité d'un système de versioning
- Niveau de fiabilité requis pour la source de données

Actuellement, l'identifiant suit le format : `prod_<brand>_<sku>`

## Tests d'intégration

Mise en place de tests d'intégration via TestContainers avec une instance Elasticsearch pour valider le fonctionnement
de bout en bout.

## Optimisation du traitement des données volumineuses

Le programme gère actuellement la lecture de fichiers volumineux via `XMLStreamReader`, mais effectue l'écriture dans
Elasticsearch en un seul lot. Deux axes d'amélioration sont envisageables :

- Optimisation des paramètres du bulkProcessor
- Implémentation d'une solution de traitement par lots (ex: Spring Batch) si nécessaire

## Nettoyage post-traitement

Ajout d'une étape de suppression automatique du fichier XML après son traitement et son indexation dans Elasticsearch.

## Sécurisation d'Elasticsearch

Implémentation de l'authentification Elasticsearch dans la configuration `.env`