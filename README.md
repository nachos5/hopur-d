## Folders

**resources:**
- Myndir o.fl.
- Dæmi um notkun
    
```java
login = new Dialog<>();
login.setGraphic(new ImageView(this.getClass().getResource("/login.png").toString()));
```

**Resource Bundle 'languages'**
- Allir strengir sem eru í GUI
- Dæmi um notkun

- Java kóði hvernig string er bindað
```java
@FXML
public MenuItem en;
en.textProperty().bind(Language.createStringBinding("MenuBar.english"));
```
- FXML itemið
```FXML
<MenuItem fx:id="en" mnemonicParsing="false" onAction="#setLanguage" text="%MenuBar.english" />
```

- Enska er default Locale, til að bæt við breytu, `right-click > Jump to Source code` eða `F6` á Resource Bundle
- Breytur heita alltaf `className.itemName`
```properties
# Í languages.properties
MenuBar.english=English

# Í languages_is.properties
MenuBar.english=enska
```


### .env

- `fellilisti við hliðin á run > edit configuration` og fylla í `Enviroment variables`
```
DB_URL="jdbc:postgresql://localhost/myDatabase"
DB_USER="myUser"
DB_PASSWORD="myPassword"
```

- DbMain.java notar `System.getenv` til að ná í database upplýsingarnar.
- Þurfið líklegast líka að add'a `lib` möppunni manually í libraries, er gert í `file > project structure > libraries > plúsinn` og svo velja lib möppuna

#### Changes
- schema nota núna nýtt schema `daytrip` sem er með `AUTHORIZATION` á `db_user` (postgres eða custom user)
- Þar þá alltaf að skrifa schema fyrir framan töflu, `daytrip.trip` eða `SELECT * FROM daytrip.trip` í kóða

* **Postgres**
   * `\dn` - Listar upp schemas
   * `\dt daytrip.*` - Listr upp relations í schema daytrip
