# Build keretrendszer és CI beüzemelése

A build keretrendszernek a Maven-t használtam. Mivel ekkor még a projekt struktúra nem volt a Maven-szerint ajánlott formában, a buildeléshez szükséges pom.xml fájlban külön meg kellett adnom a forrás mappát és a resources mappáját. Ezek után a projekt már buildelhető volt.

CI-t a Github Actions-el valósítottam meg. Miután a buildeléshez szükséges lépéseket már megtettem, létrehoztam a maven.yml fájlt, amiben megadtam a szükséges taszkok végrehajtását, azaz a projekt buildelését.

# Projekt struktúra módosítása

Miután elkészültem a build és a CI beüzemelésével, felmerült, hogy a projekt struktúrját úgy kéne megváltoztatni, hogy az a Maven szerint ajánlott formában legyen, és a felesleges fájlokat ell kéne távolítani.
Eltávolítottam a buildeléskor létrejött out mappát, valamint a resources mappát ami kétszer szerepelt a projektben. Áttanulmányoztam az ajánlott struktúrát, és módosítottam a projekten azok szerint, a forrásfájlokban a packageket is módosítottam szükség szerint. Végül a pom.xml tartalmát kellett megváltoztatnom, hogy a buildelés az új struktúránál is működjön.

# Összefoglalás:

A munkám során jobban megismertem a Mavennel való buildelés és a Github Actions-nal való CI menetét, valamint a Maven által ajánlott projekt struktúrát. A Github kezelésére is több tapasztalatot szereztem.
