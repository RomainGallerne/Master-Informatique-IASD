# I. Commandes de base

##### 1. Vérifier existance de la DB
`curl -X GET prodpeda-couchdb-2.infra.umontpellier.fr:5984`

##### 2. Supprimer la DB
`curl -X DELETE $COUCH3/gallerne_occitanie`

# II. Création et alimentation de la base de données

##### 1. Créer une base de données occitanie
`curl -X PUT $COUCH3/gallerne_occitanie`

##### 2. Ajout des fichiers de données
`curl -X POST $COUCH3/gallerne_occitanie/_bulk_docs -d @DataTP/aveyron.json -H "Content-Type: application/json"`

`curl -X POST $COUCH3/gallerne_occitanie/_bulk_docs -d @DataTP/gard.json -H "Content-Type: application/json"`

`curl -X POST $COUCH3/gallerne_occitanie/_bulk_docs -d @DataTP/hauteGaronne.json -H "Content-Type: application/json"`

`curl -X POST $COUCH3/gallerne_occitanie/_bulk_docs -d @DataTP/herault.json -H "Content-Type: application/json"`

`curl -X POST $COUCH3/gallerne_occitanie/_bulk_docs -d @DataTP/regions_partiel.json -H "Content-Type: application/json"`

# III. Appropriation de la base de donnée

##### 1. lister les informations générales concernant le serveur couchdb, à l’aide du mécanisme GET

`curl -X GET prodpeda-couchdb-2.infra.umontpellier.fr:5984`
    
##### 2. lister les informations générales concernant concernant la base occitanie, à l’aide du mécanisme GET. Pouvez vous connaître le nombre de documents contenus dans la base occitanie ?

`curl -X GET $COUCH3/gallerne_occitanie`

Il y a 314 documents dans la base gallerne_occitanie.
    
##### 3. lister tous les documents de la BD
`curl -X GET $COUCH3/gallerne_occitanie/_all_docs`
    
##### 4. faire afficher le contenu d’un document. Quel est son numéro de révision ? Comment savoir si ce document a déjà  ́eté modifié ?
`curl -X GET $COUCH3/gallerne_occitanie/34199`

Son numéro de révision est 1.
Si le document avait été modifié, son numéro serait >1.

# IV. Définition de vues

### IV.A MAP seulement

##### 1. donnez toutes les informations sur les régions (de type old region) de la base
```js
function (doc) { //Map
  if (doc.type === "old_region"){
    emit(doc._id, doc);
  }
}
```

##### 2. donner les noms (clés) et latitude et longitude de chaque commune
```js
function (doc) { //Map
  if (doc.type === "commune"){
    emit(doc._id, [doc.latitude, doc.longitude]);
  }
}
```

##### 3. donner le code insee (clé), le département, la latitude et la longitude de MONTPELLIER (nom de la commune)
```js
function (doc) { //Map
  if (doc.type === "commune"){
    if ( doc.nom === "MONTPELLIER"){
      emit(doc._id, [doc.dep, doc.latitude, doc.longitude]);
    }
  }
}
```

##### 4. donnez le nom et le prénom de la présidente de la région Occitanie
```js
function (doc) { //Map
  if (doc.type === "region"){
    if ( doc._id === "occitanie"){
      emit(doc._id, [doc.president.prenom, doc.president.nom]);
    }
  }
}
```

### IV.B MAP REDUCE

##### 1. donner le nombre de communes au total puis par département et enfin par région (old region)
```js
function (doc) { //Map
  if (doc.type === "commune"){
    emit(doc._id, 0);
  }
}

_count
```

```js
function (doc) { //Map
  if (doc.type === "commune"){
    emit(doc.dep, 0);
  }
}

_count
```

```js
function (doc) { //Map
  if (doc.type === "commune"){
    emit(doc.old_reg, 0);
  }
}

_count
```


##### 2.3.4. donner le nombre d’habitants par commune, département, régions en 1985
```js
function (doc) { //Map
  if (doc.type === "commune"){ 
    for (var p in doc.populations){
      if (doc.populations[p].pop_1985){
        emit([doc.old_reg, doc.dep, doc._id], doc.populations[p].pop_1985);
      }
    }
  }
}

_sum
group_level = {1,2,3}
```

### IV.C Autres requêtes

##### 1. donner les communes qui ont vu leurs populations d ́ecroître entre 1985 et 1995

# PAS FINI
```js
function (doc) { //Map
  if (doc.type === "commune"){
    for (var p in doc.populations){
      var p85 = doc.populations[p].pop_1985
      var p95 = doc.populations[p].pop_1995
    }

    if (p85 && p95 && p85 < p95){
      emit([doc._id, 95], "decroissance");
    }
  }
}
```

##### 2. donner les informations sur la nouvelle r ́egion Occitanie ainsi que sur les anciennes r ́egions Languedoc-Roussillon et Midi-Pyrénées (forme de jointure)

# V. Distribution de la base de données

##### 1. quels sont les nœuds mobilis ́es pour l’organisation du serveur, et la gestion de la base ?

##### 2. Combien de partitions sont d ́efinies (avant recopie) ? Quel est le nombre de copies ? Combien de partitions r ́epliqu ́ees sont d ́efinies ?

##### 3. Comment savoir dans quelle(s) partition(s) se trouve un des documents de la base ?

##### 4. Est ce que des copies de toutes les partitions sont pr ́esentes sur tous les nœuds ?

##### 5. Est que toutes les lectures sont consistantes ?