# SonarCloud
A feladatom egy nagyobb része a kóddal való ismerkedés a keretrendszer felállítása, és a program futtatható állapotba tétele. Ezután a Sonarcloudot erre a projektre rá kellett illeszteni. Ezzel több probléma is adódott, a fő repository jogosultsága miatt. Ezért készítettem egy mirror repository-t a projektre és azon hajtottam végre a statikus analízist. A változtatások amiket megtaláltam azt pedig manuálisan javítottam a kódban, és pusholtam a saját branchemre.

# javítások a kódban
A SonarCloud rengetek hibát és codesmlett jelzett, ezeket áttekintettem, és azt láttam, hogy rengeteg ismétlődés van bennük, és sok közűlük nem valós hiba, vagy probléma, vagy tényleg csak egy apró változatásra van igény. Megpróbáltam nagyobb hibákat keresni, amikre egyértelmű a javítás. Ilyen volt egy if hiba, egy getter setter felcserélés, és haszontalan kódsorok törlése, egy if feltétel módosítása. Láttam a random()-al kapcsolatos figyelmeztetést is, de azt a társam már sonarlintel megtalálta, és javította, szóval ahhoz nem nyúltam.

# Eredmények és tanulságok összefoglalása:
Jó volt kipróbálni egy ilyen eszközt. Rendkívül segíti a program szisztematikus jobbátételét. A projekt segítségével elméjítettem a git-es repository kezelés tudásomat.