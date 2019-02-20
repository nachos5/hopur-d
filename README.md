Held að það sé best að við notum local database á meðan við búum til forritið. Þurfið að búa til ykkar eigið database.
Í DbMain.java nota ég System.getenv til að ná í database upplýsingar, held að það sé sniðugt svo við þurfum ekki að breyta alltaf í okkar settings.

Fer eftir stýrikerfi en hjá mér í Ubuntu setti ég env variables með þvi að breyta þessum file /etc/environment og gerði
DATABASE_URL="jdbc:postgresql://localhost/hopurd" etc... og já restarta tölvunni

Þurfið að stilla DATABASE_URL_HOPURD, DATABASE_USER_HOPURD og DATABASE_PASSWORD_HOPURD
lét breyturnar enda á hopurd af því að þetta var að conflicta við vefforritun