Held að það sé best að við notum local database á meðan við búum til forritið. Þurfið að búa til ykkar eigið database.
Í DbMain.java nota ég System.getenv til að ná í database upplýsingar, held að það sé sniðugt svo við þurfum ekki að breyta alltaf í okkar settings.

Getið sett inn env variables í main fallið með því að fara í fellilistann hjá run takkanum og fara í edit configurations.
Svo stilla DB_URL, DB_USER og DB_PASSWORD, hjá mér er t.d. DB_URL="jdbc:postgresql://localhost/hopurd"

JDBC getur ekki tekið við venjulegum postgres url sem inniheldur username og password heldur þarf að skipta þessu svona upp.

Þurfið líklegast líka að adda lib möppunni manually í libraries, er gert í file ->
project structure -> libraries -> plúsinn og svo velja lib möppuna