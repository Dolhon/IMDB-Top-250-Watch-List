# IMDB Top 250 Watch List
IMDB 250 parhaan elokuvan katselulista.

Tekijä: Sami Antila

## Kuvaus
Internet elokuvatietokannan (IMDB.com) 250 parhaan elokuvan katselulista. Listan avulla
voidaan seurata kuinka monta elokuvaa listalta on nähnyt ja käyttäjä voi merkitä elokuvan
listaan katsomisen jälkeen.
Datan tuonti
IMDB API:lle ei löytynyt dokumentointia. Tutkimisen jälkeen päädyttiin kokeilemaan kilpailevan
Rotten Tomatoes sivuston API:a, jossa oli hyvä dokumentointi ja API käyttöön sai avaimen
helposti rekisteröitymällä (Esimerkki löytyy luokasta RottenTomatoesAPI). Ongelmaksi kuitenkin
muodostui järkevän listan ulos saaminen. Paras “top” -lista mitä API:lla sai pihalle oli parhaat
tämänhetkiset vuokraelokuvat, joka ei sopinut projektiin.
Tutkimalla vaihtoehtoja löytyi keskustelu IMDB:n vaihtoehtoisista rajapinnoista 
(http://www.imdb.com/interfaces), josta pystyis lataamaan ratings.list nimisen tiedoston, joka sisälsi
elokuvat käyttäjä arvosanojen keskiarvon mukaan. Näin päästiin käsiksi IMDB:n top 250 listaan.
Data tuodaan tietokantaan lukemalla elokuvien tiedot rivi kerrallaan siistitystä ratings.list
tiedostosta. Elokuvat tulevat tietodostosta parhaus järjestyksessä ja ne tallennetaan
tietokantaan samassa järjestyksessä. Elokuvista tallennetaan arvosana ja nimi.

## Ominaisuudet
- Datan hakeminen IMDB:n 250 parhaasta elokuvasta.
- Tallenna elokuvalista tietokantaan.
- Lue elokuvalista tietokannasta.
- Tallenna käyttäjäkohtaisesti tietokantaan käyttäjän katsomat elokuvat
- Elokuvan avain
- Käyttäjän avain
- Katsomis päivämäärä
- Päivämäärän muunnokset JSF<->MySQL ja toisinpäin
- Suodata lista (kaikki, katsotut, katsomattomat)
- Listan jakaminen useaan osaan (pagination)
- Listan järjestyksen muuttaminen
- Statistiikka
- Montako prosenttia käyttäjä on katsonut listan elokuvista
- Diagrammi montako elokuvaa käyttäjä on katsonut minäkin päivän

## Tietokanta
- Users
- Käyttäjän tiedot
- Movies
- Elokuvan nimi
- Elokuvan arvosana
- WatchedMovies
- Elokuvan katsomis päivämäärä.
- Elokuvan avain
- Käyttäjän avain

## Käytetyt teknologiat
- JSF 2.2
- PrimeFaces 5.1 framework for JSF
- MySQL 5.6.14

![alt text](https://github.com/Dolhon/IMDB-Top-250-Watch-List/blob/master/img/tech.JPG "")

## Luokat
- MovieBean
- Päätaulu, kaikki käyttöliittymään liittyvä logiikka
- PrimeFaces toiminnot (chart)
- User
- Käyttäjätiedot
- WatchedMovie
- Katsottu elokuva ja katsomis päivämäärä
- Movie
- Elokuvan nimi, järjestysnumero ja arvosana
- imdbData
- Käytetään tuomaan elokuvalista ratings.list tiedostosta
- Stats
- Laskee käyttäjän lista statistiikat
- Prosenttia katsottu
- Montako elokuvaa minäkin päivänä
- RottenTomatoesAPI
- Rotten Tomatoes sivuston API testauksia
- Database
- Hoitaa kaiken tietokantaan liittyvän logiikan
- Yhdistäminen ja yhteyden purku
- Tietokannan luku, kirjoitus, päivitys

![alt text](https://github.com/Dolhon/IMDB-Top-250-Watch-List/blob/master/img/class_diagram.JPG "")

## Käyttöliittymä hahmotelma

![alt text](https://github.com/Dolhon/IMDB-Top-250-Watch-List/blob/master/img/gui_sketch.JPG "")

## Käyttöliittymä toteutunut

![alt text](https://github.com/Dolhon/IMDB-Top-250-Watch-List/blob/master/img/gui.JPG "")

## Käyttöönotto
Mikäli harjoitustyö halutaan ottaa käyttöön löytyy tietokannasta kopio (mysql dumb -tiedosto)
palautus zip-tiedoston juuresta. Lisäksi tulee tarkistaa database luokasta tietokannan osoite ja
salasana. Jos halutaan kokeilla datan tuontia, täytyy sijoittaa palautus zip-tiedoston ratings.list
tiedosto D:\ aseman juureen tai muuttaa tiedoston sijaintia imdbData luokasta.

## Loppusanat
Sivustot kuten imdb.com ja rottentomatoes.com suojelevat dataansa. Sivustojen API:a
suojelevat mahtavan kokoiset lisenssi dokumentit, joihin tämän projektin aikana tutustuttiin
vain pintapuolisesti. Jos palvelusta tulisi julkinen olisi joko uhrattava paljon miestyötunteja
sopimusten lukemiseen tai palkattava juristi. Vaihtoehtoisesti voi ottaa riskin ja jäädä
odottelemaan “cease and desist” -kirjettä.
PrimeFaces oli mukava yllätys, hyvin dokumentoitu ja paljon esimerkkejä. Se tuli tutuksi JavaEE
opintojakson viimeisissä etätehtävissä, suurin osa DataTable dynaamiseen täyttöön liittyvistä
ratkaisuista käytti PrimeFace:a. PrimeFace myös ratkaisi statistiikkaan liittyvät esitysgrafiikan
generointi ongelmat.
Ajankäyttöön ollaan tyytyväisiä, suunnitellut toiminnot toteutuivat hieman etuajassa.
Loppujenlopuksi harjoitustyö meni kuitenkin melkein 20 tuntia ylitöiksi, se johtui lähinnä
vapaaehtoisesta viilaamisesta ja PrimeFaces framework:n tutkimisesta, lisä ominaisuuksien
lisäämisistä. Aika ei missään nimessä mennyt hukkaan ja kertoo enemmän siitä että työ oli
tekijälle mieleinen.
Projektissa seuraava kehityksen kohde olisi käyttäjätilien hallinta.
Harjoitustyöstä jäi hyvä tunne, koodi on hyvin organisoitu, luokkarakenne selkeä. JSF
ja PrimeFaces kanssa oli helppo työskennellä, molemmat tähän mennessä parhaiten
dokumentoituja. Ainoa mikä olisi voinut tehdä tästä vielä mukavamman projektin olisi IMDB:n
hyvin dokumentoitu API mistä olisi saanut elokuvien tiedostot, erityisesti elokuvien pituudet.

