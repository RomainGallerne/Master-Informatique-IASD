# I. Création et alimentation d’une base de données vaccination contre le Covid-19

## 1.1 Sans schéma et structure JSON
`curl -X PUT $COUCH3/gallerne_vaccination?q=8`

* Les deux modèles comportent bien un attribut de type : "type".
* Les deux modèles ont des dates d'enregistrement.
* Vaccin1 contient l'attribut "_id" bien défini, ce qui n'est pas le cas de vaccin2.
* Le premier modèle contient des informations redondantes contenus dans plusieurs documents.
* Le second modèle groupe par attribut de date et de département, ce qui donne une base plus concise (14 000 contre 46 000). ́e

`curl -X POST $COUCH3/gallerne_vaccination/_bulk_docs -d @"TP4 CouchDB/vaccin2.json" -H "Content-Type: application/json"`

# II. Appropriation de la base de données

## 2.1 Ajout de documents de type ”departement”

`curl -X POST $COUCH3/gallerne_vaccination/_bulk_docs -d @"TP4 CouchDB/departements.json" -H "Content-Type: application/json"`

```js
function (doc) {
if (doc.type==’departement’)
emit(doc.type,doc.population);
}
function (keys, values, rereduce) {
return Math.min.apply({}, values);
}
```
Que renvoie cette requête au niveau map, puis au niveau reduce et enfin au niveau rereduce ?
* Map -> La population de chaque département
* Reduce -> La population la plus faible parmis celles de ces départements
* Rereduce (mettre None en group level) -> Idem

## 2.2 Jeu de questions à définir en fonction du modèle choisi

### Informations générales
`curl -X GET $COUCH3/gallerne_vaccination`
Il y a 1165 documents dans la base.

### Liste tous les documents de la BD
`curl -X GET $COUCH3/gallerne_vaccination/_all_docs`

### Faire afficher le contenu d'un document
`curl -X GET $COUCH3/gallerne_vaccination/9a871fd8c3b64e342307918a1f47918a`

# III. Définition de vues

## 3.1 MAP et MAP/REDUCE

**1. renvoyez pour tous les documents de type couverture vaccinale du département de l’Hérault (34), l’identifiant du document (clé doc. id) et le jour de vaccination (valeur doc.jour)**

```js
function (doc) { //Map
  if (doc.type === "couverture_vaccinale" && doc.dep === '34'){
    emit(doc._id, doc.jour);
  }
}
```
**2. donnez le nombre de documents de type couverture vaccinale du département de l’Hérault (34) (clé doc.dep, valeur 1)**

```js
function (doc) { //Map
  if (doc.type === "couverture_vaccinale" && doc.dep === '34'){
    emit(doc._id, 0);
  }
}

count
```

*Il y en a 290.*

**3. donnez le nombre de documents de type couverture vaccinale du département de l’Hérault (34) pour chaque année  ́ecoulée.**

```js
function (doc) { //Map
  if (doc.type === "couverture_vaccinale" && doc.dep === '34'){
    var d = new Date(doc.jour) ;
    emit(d.getFullYear(), 0);
  }
}

count
```

*5 en 2020 et 285 en 2021*

**4. donnez le nombre de documents de type couverture vaccinale par département, par année et par mois  ́ecoulés.**

```js
function (doc) { //Map
  if (doc.type === "couverture_vaccinale"){
    var d = new Date(doc.jour) ;
    emit([doc.dep, d.getFullYear(), d.getMonth()], 0);
  }
}

count
```

**5. donnez pour les documents de type couverture vaccinale pour le vaccin Pfizer, l’identifiant du document (clé doc. id) et la date et le département de vaccination (valeur : doc.jour et doc.dep)**

```js
function (doc) { //Map
    if (doc.type === "couverture_vaccinale"){
        for (var elem of doc.vaccinations){
            if (elem["vaccin"] === "Pfizer"){
                emit(doc._id, [doc.dep, doc.jour]);
            }
        }
    }
}
```

**6. donnez le nombre de documents de type couverture vaccinale pour le vaccin Pfizer par département**

```js
function (doc) { //Map
    if (doc.type === "couverture_vaccinale"){
        for (var elem of doc.vaccinations){
            if (elem["vaccin"] === "Pfizer"){
                emit(elem["vaccin"], 0);
            }
        }
    }
}

count
```


*Il y en a 1160.*

**7. donnez le nombre de documents de type couverture vaccinale pour le vaccin Pfizer par département, par an et par mois**

```js
function (doc) { //Map
    if (doc.type === "couverture_vaccinale"){
        for (var elem of doc.vaccinations){
            if (elem["vaccin"] === "Pfizer"){
                var d = new Date(doc.jour) ;
                emit([doc.dep, d.getFullYear(), d.getMonth()], 0);
            }
        }
    }
}

count
```

**8. donnez la somme de dose1 (et autres statistiques) pour le vaccin Pfizer, par département, par an et par mois**

```js
function (doc) { //Map
    if (doc.type === "couverture_vaccinale"){
        for (var elem of doc.vaccinations){
            if (elem["vaccin"] === "Pfizer"){
                var d = new Date(doc.jour);
                var doses = [
                    elem["doses"][0]["dose1"],
                    elem["doses"][1]["dose2"],
                    elem["doses"][2]["dose3"]
                ];
                emit(
                    [doc.dep, d.getFullYear(), d.getMonth()],
                    doses
                );
            }
        }
    }
}

sum
```


# IV. Distribution de la base de données

**1. Combien de partitions sont définies (avant recopie) ? Quel est le nombre de copies ? Combien de partitions répliquées sont définies ?**

`curl -X GET $COUCH3/gallerne_vaccination/_shards`

**2. Comment savoir dans quelle(s) partition(s) se trouve un des documents de la base ?**

`curl -X GET prodpeda-couchdb-2.infra.umontpellier.fr:5984/gallerne_vaccination/_shards/9a871fd8c3b64e342307918a1f47918a`

**3. Est ce que des copies de toutes les partitions sont présentes sur tous les nœuds ?**

`curl -X GET $COUCH3/gallerne_vaccination/_shards`
Noeud couchdb-4 abscent de la première partition. 