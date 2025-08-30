# Suunnitelman kuvat

- alkuperäiset suunnitelmat [TIMissä](https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/suunnitelmat)

Kuitenkin myös ohessa:

# 1. Peliasetusrekisteri

Ohjelman tarkoitus on pystyä pitämään kirjaa CS:GO pelaajien peliasetuksista.

### 1.1 Mitä tietoja tarvitaan?


    nimimerkki
    joukkue
    hiiren herkkyys
    DPI
    näytön tarkkuus
    kuvasuhde
    skaalaus
    virkistystaajuus (Hz)

### 1.2 Mitä ominaisuuksia rekisteriltä halutaan?


    profiilien lisääminen
    profiilien poistaminen
    tietyn profiilin tietojen hakeminen nimimerkin/joukkueen perusteella
    tietyn profiilin tietojen muuttaminen
    profiilien hakeminen tiettyjen peliasetusten avulla?
    eDPI:n laskeminen?
    Kaikkien profiilien eDPI keskiarvo

### 1.3 Tallennustiedostojen muoto

Ohjelman tiedot tallennetaan seuraavanlaisiin tekstitiedostoihin:

```peliasetusrekisteri\profiilit.dat``` - relaatiokannan päätaulu

```
; Kenttien järjestys tiedostossa on seuraava:
;pid|nimimerkki     |joukkue|hiiren herkkyys|DPI    |näytön tarkkuus|kuvasuhde  |skaalaus   |virkistystaajuus
1   |s1mple         |1      |3.09           |400    |1280x960       |4:3        |stretched  |240
2   |allu           |2      |3.3            |400    |1024x768       |4:3        |black bars |144 
4   |ropz           |3      |1.77           |400    |1920x1080      |16:9       |stretched  |240
5   |Jamppi         |2      |1.25           |800    |1440x1080      |4:3        |stretched  |144
```

```peliasetusrekisteri\joukkueet.dat``` - joukkueet relaation avulla
```
jid|joukkue
1  |Natus Vincere
2  |ENCE
3  |mousesports
```
## 2. Ohjelman käyttö

### 2.1 Ohjelman käynnistys

Ohjelma käynnistetään klikkaamalla kerho.jar-ikonia tai antamalla komentoriviltä komento

```java -jar peliasetusrekisteri.jar ```

### 2.2 Pääikkuna

Kun ohjelma on käynnistynyt on näkyvillä ohjelman pääikkuna:

<img width="500" height="350" alt="paaikkuna" src="https://github.com/user-attachments/assets/83f0b8d2-6991-4722-9026-68016b59b752" />

Pääikkunassa on seuraava menurakenne:

```Tiedosto          Muokkaa                   Apua
========          =======                   =====
Tallenna          Lisää uusi profiili       Apua
Lopeta            Muokkaa profiilin tietoja Tietoja...
                  Poista profiili...
```

### 2.3 Hakeminen

Pääikkunan vasemmassa reunassa näkyy Hakuehto. Tästä voi valita minkä kentän mukaan etsitään. Tämän jälkeen tekstikenttään voi syöttää hakuehdon ja listaan tulee vain ne jäsenet joille haku toteutuu. Hakutermi saa löytyä valitusta kentästä mistä kohti vaan. Esimerkiksi jos kirjoitetaan hakuehtoon s, niin haetaan kaikki jäsenet joiden nimessä on s jossakin kohti.

Löytyneet jäsenet lajitellaan valitun hakukentän perusteella.

### 2.4 Muokkaaminen

Profiilin tietoja päästään muokkaamaan, kun profiili on ensin valittu ja sitten painetaan "muokkaa" painiketta, joka avaa uuden muokkausdialogin. Samalla ilmestyy "tallenna" ja "peruuta" painikkeet.

<img width="265" height="289" alt="uusiprofiili" src="https://github.com/user-attachments/assets/cf909add-d885-4bc2-9d62-8d04fcce226d" />

Valittua jäsentä voidaan muokata menemällä tietoihin oikeaan kohtaan ja kirjoittamalla uusi arvo. Jos tietoon syötetään jotakin mikä ei kelpaa, tulee tästä ilmoitus:

``` 
Esim jos hiiren herkkyys kohtaan on kirjoitettu kirjaimia

tulee ilmoittaa

Väärä muoto, käytä numeroita.
```
Samalla virheellinen syöttökenttä menee punaiseksi.

### 2.5 Uusi profiili

"Uusi profiili" -painike tai Menu --> Muokkaa --> Lisää uusi profiili

Avaa uuden, tyhjän "profiilin tiedot" -ikkunan, kuten kohdassa 2.4.

## 3. Menutoiminnot yksityiskohtaisemmin

### 3.1 Tallenna

Tallentaa tiedostoihin tehdyt muutokset

### 3.2 Lopeta

Sulkee ohjelman ja tallentaa tehdyt muutokset

### 3.3. Lisää uusi profiili

ks. 2.5

### 3.4 Muokkaa profiilin tietoja

ks. 2.4

### 3.5 Poista profiili

Avaa dialogin "Poistetaanko profiili: esimerkki" jossa vaihtoehdot "kyllä" ja "ei"

Vaihtoehto "Kyllä" poistaa profiilin. 

<img width="418" height="132" alt="poistaminen" src="https://github.com/user-attachments/assets/4ce351e5-94eb-490f-ac30-ba4e7cefec1d" />

### 3.6 Apua

Avaa suunnitelma TIM-sivun

### 3.7 Tietoja

Avaa uuden dialogin, josta käy ilmi ohjelman nimi, versio ja tekijä. 

<img width="232" height="255" alt="tietoja" src="https://github.com/user-attachments/assets/406c1631-7431-4f96-bb1c-8702f42b1688" />

## 4. CRC-kortit

### 4.1 Profiili-luokka (Profiili)

``` 
|------------------------------------------------------------------------|
| Luokan nimi: Profiili                              | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    | -                 |
| - tietää profiilin kentät (nimimerkki, joukkue jne)| -                 |
| - osaa tarkistaa tietyn kentän oikeellisuuden      | -                 |
| - osaa muuttaa 1|s1mple|..| -merkkijonon jäsenen   |                   |
|   tiedoiksi                                        |                   |
| - osaa antaa merkkijonona i:n kentän tiedot        |                   |
| - osaa laittaa merkkijonon i:neksi kentäksi        |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

### 4.2 Joukkue-luokka (Joukkue)

```
|------------------------------------------------------------------------|
| Luokan nimi: Joukkue                               | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    |                   |
| - tietää joukkueen kentät                          |                   |
| - osaa tarkistaa tietyn kentän oikeellisuuden      |                   |
| - osaa muuttaa 1|Natus Vincere| -merkkijonon jouk- |                   |
|   kueen tiedoiksi                                  |                   |
| - osaa antaa merkkijonona i:n kentän tiedot        |                   |
| - osaa laittaa merkkijonon i:neksi kentäksi        |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

### 4.3 Peliasetusrekisteri-luokka (Peliasetusrekisteri)

```
|------------------------------------------------------------------------|
| Luokan nimi: Peliasetusrekisteri                   | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    | - Profiilit       |
| - huolehtii profiilit ja joukkueet -luokkien väli- | - Joukkueet       |
|   sestä yhteistyöstä ja välittää näitä tietoja pyy-| - Profiili        |
|   dettäessä                                        | - Joukkue         |
| - lukee ja kirjoittaa rekisterin tiedostoon        |                   |
|   pyytämällä apua avustajiltaan                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

### 4.4 Profiilit-luokka (Profiilit)

```
|------------------------------------------------------------------------|
| Luokan nimi: Profiilit                             | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    | - Profiili        |
| - pitää yllä varsinaista profiilirekisteriä eli    | -                 |
|   osaa lisätä ja poistaa profiilin                 | -                 |
| - lukee ja kirjoittaa profiilin tiedostoon         |                   |
| - osaa etsiä ja lajitella                          |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

### 4.5 Joukkueet-luokka (Joukkueet)

```
|------------------------------------------------------------------------|
| Luokan nimi: Joukkueet                             | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    | - Joukkue         |
| - pitää yllä varsinaista joukkuerekisteriä eli     | -                 |
|   osaa lisätä ja poistaa joukkueen                 | -                 |
| - lukee ja kirjoittaa joukkueet tiedostoon         |                   |
| - osaa etsiä ja lajitella                          |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

### 4.6 Näyttö-luokka (Naytto)

```
|------------------------------------------------------------------------|
| Luokan nimi: Naytto                                | Avustajat:        |
|-------------------------------------------------------------------------
| Vastuualueet:                                      |                   |
|                                                    | - Peliasetusrekis-|
| - hoitaa kaiken näyttöön tulevan tekstin           |   teri            |
| - hoitaa kaiken tiedon pyytämisen käyttäjältä      | - Profiili        |
|                                                    | - Joukkue         |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|                                                    |                   |
|-------------------------------------------------------------------------
```

## 5. Tietorakennekuva

<img width="779" height="786" alt="image" src="https://github.com/user-attachments/assets/9e35672a-00fa-4ae8-ba28-4b80f1a4e184" />
