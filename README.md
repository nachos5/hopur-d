## Git
- Uppfæra 'my_branch'
    - `git checkout master`
        - Skipta yfir á local master branch
    - `git pull origin master`
        - Uppfæra local master branch með remote master
    - `git checkout 'my_branch'`
        - Skipta yfir á 'my_branch'
    - `git merge master`
        - Merge 'my_branc' við master
    - `git push origin my_branch`
        - Uppfæra remote 'my_branch' með local 'my_branch'

### Merge
- Guðmundur sér um að merge'a við masterinn (origin master)
- Til að auðvelda það, þá er gott að uppfæra sitt branch reglulega, eða áður en það er push'að á sitt eigið branch.

## Login
- user: `admin`
    - pw: `admin`
- user: `user`
    - pw: `user`

## Folders

**resources:**
- Myndir o.fl.
- Dæmi um notkun:

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


### Guðmundur - 13/04

- Setti upp skemað, módel, enums, query classa og tests fyrir allt sem við þurfum (held ég).
- Er allt í database möppunni og endilega skoðið vel, í Tests.java er gott að sjá hvernig ég er að sækja gögnin og nota.
- Enuminn eru mjög ófullkominn núna en þetta er bara einhver grunnur.
- Testin eru til að athuga hvort eitthvað brotnar við breytingar, svo það er gott ef við setjum öll queries (fyrir utan inserts)
  þangað inn.