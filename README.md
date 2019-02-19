Held að það sé best að við notum local database á meðan við búum til forritið.
Í DbMain.java nota ég System.getenv til að ná í database upplýsingar, held að það sé sniðugt svo við þurfum ekki að breyta alltaf í okkar settings.
Fer eftir stýrikerfi en hjá mér í Ubuntu setti ég env variables með þvi að breyta þessum file /etc/environment og gerði
DATABASE_URL="jdbc:postgresql://localhost/hopurd" etc... og já restarta tölvunni