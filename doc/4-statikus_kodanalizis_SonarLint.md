# Statikus kód analízis SonarLint segítségével

Munkám során az alkalmazás teljes kódját átnéztem a SonarLint alkalmazás segítségével. Az alkalmazás rámutatott nagyon egyszerű hibákra is, mint például elég, ha egy osztályban egyszer hozunk létre egy Random példányt, és azt használjuk fel újra és újra, mert az kevesebb erőforrás. De mutatott érdekesebb megoldásokat is, mint például string erőforrások kiszervezése, vagy kódrészletek teljes átalakítása.
Külön érdekesség volt, hogy a VS Code-os SonarLint kicsit más javaslatokat tett, mint az IntelliJ-s, így a két IDE-t együtt használva rengeteget javítottam a kód minőségén.
Az analízis csúcspontja volt, mikor az alkalmazás segítségével megtaláltam egy olyan bug okozóját, amit anno egy éjszakán keresztül kerestünk.
Azonban néha összetettebb logikai függvényeknél az ajánlatai hamisak voltak, így nem szabad minden javaslatának hinni. 

# Eredmények és tanulságok összefoglalása

A SonarLint segítségével egy sokkal szebb, könnyebben bővíthető és jobban átlátható kódot kaptunk. Nagyon jó kis alkalmazásnak tartom, valószínűleg a továbbiakban is fogom használni. 
Sokat segít olyan egyszerű hibák felderítésében, amiket én akár észre se vennék (pl. lemaradt {} if után). Illetve a kód minőségén is tényleg sokat javít, főleg olyan esetben, ha egyszerre több ember ír egy adott programot, és egyikük kódolási stílusa eltér a standardtől, ezzel elrontva az olvashatóságot. 
Sokkal jobb ránézni a segítségével kijavított kódra, mint ami előtte volt, illetve plusz pont, hogy egy egyszerű kód analízis közben még bugokat is tudtam javítani.
