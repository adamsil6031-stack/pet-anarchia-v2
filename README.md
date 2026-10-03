# Pet Swap (Fabric, Minecraft 26.1.2) - wersja wizualna

Klawisz **[** otwiera GUI. Przycisk "Zamien trzymany przedmiot na peta" zmienia
**wyglad** przedmiotu w rece (w rece, na pasku i w ekwipunku) na teksture peta.
Drugi przycisk przywraca oryginalny wyglad.

## Wazne
- Mod dziala TYLKO po stronie klienta. Serwer, w tym anarchia.gg, nadal widzi
  oryginalny przedmiot, a inni gracze tez. Zmiana jest widoczna tylko u Ciebie.
- To nie tworzy prawdziwego peta i nie da sie go z nim handlowac.
- Gdy serwer odswiezy dany slot (np. zmieni sie ilosc albo wytrzymalosc
  przedmiotu), wyglad moze wrocic do oryginalu. Wtedy uzyj przycisku ponownie.

## Wlasna tekstura peta
Podmien plik `src/main/resources/assets/petmod/textures/item/pet.png`
(16x16 PNG) i przebuduj mod.

## Uruchomienie
1. JDK 25, IntelliJ IDEA 2025.3+.
2. Otworz folder w IntelliJ, ustaw Gradle JVM na 25, poczekaj na Gradle.
3. Zadanie `runClient` (Tasks -> fabric -> runClient) odpala gre z modem.
4. `./gradlew build` -> gotowy .jar w `build/libs/` (bez `-sources`),
   wrzuc do `mods` razem z Fabric API.

Klawisz mozna zmienic w Opcje -> Sterowanie -> Pet Swap.
Gdyby Gradle zglosil zbyt stara wersje, zmien `distributionUrl` w
`gradle/wrapper/gradle-wrapper.properties` na nowsza.
