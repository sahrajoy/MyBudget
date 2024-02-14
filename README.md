MyBudget
Haushaltsbuch

Dokumentenentwicklung
Version	Beschreibung	Datum
0.1	Erste Erstellung des Dokuments 	20.01.2023
0.1.1	Erstellung der Punkte 1 bis 3.3	24.01.2023
0.1.2	Überarbeitung der Punkte 1 bis 3.3	27.01.2023
0.2.	Überarbeitung der Punkte 1 bis 3.3	30.01.2023
0.2.1	Erstellung Punkt 3.4	02.02.2023
0.3.1	Erstellung Punkt 3.5 	05.02.2023
0.3.2	Überarbeitung Punkt 3.5	07.02.2023
0.3.3	Überarbeitung Punkt 3.5	08.02.2023
0.3.4	Überarbeitung Punkt 3.5	10.02.2023
 
Inhaltsverzeichnis
 
1 Vision                                                                                                                                                                                            
1.1 Vision und Kurzbeschreibung des Projekts
MyBudget ist ein einfaches, aber effektives Finanzverwaltungstool, mit dem Sie Ihre Einnahmen und Ausgaben für einen ganzen Haushalt im Auge behalten können. Mit seiner benutzerfreundlichen Oberfläche können Sie Ihre Einnahmen und Ausgaben einfach eingeben und bereits vorplanen. Sie können sich Statistiken anzeigen lassen, um einen Überblick über Ihre finanzielle Situation zu erhalten. Durch die Verwendung von MyBudget behalten Sie Ihre Finanzen im Griff, um Ihre finanziellen Ziele zu erreichen.
2 Grobe Spezifikation                                                                     
2.1 Zusammenhang mit bereits bestehenden Systemen 
Es gibt keine Anbindung an andere Softwaresysteme.
2.2 Überblick über die geforderte Funktionalität
Für die Anwendung müssen folgende Funktionen verfügbar sein: 

Übersicht:
Eine Übersicht aller Einnahmen und Ausgaben in der es möglich ist die Kategorien der Einnahmen oder Ausgaben zu sehen, mit der Summe aller Einträge einer Kategorie, für einen Zeitraum von einem Monat oder Jahr. Über einen Button besteht die Möglichkeit zu einer Detailansicht einer Kategorie zu kommen. 

Benutzer:
Es wird die Möglichkeit geben einem Haushalt bis zu sechs Benutzer zuzuteilen, die man über ein Feld das immer zu sehen ist, leicht wechseln kann um von der übergreifenden Haushalt-Ansicht zur Benutzer-Ansicht zu kommen in der dann nur jene Einträge angezeigt werden die einem Benutzer zugeteilt wurden.

Favoriten:
Desweiteren ist es möglich Kategorien zu favorisieren, wo man sie schnell unter dem Punkt „Favoriten“ in der Detailansicht zu sehen bekommt. 

Detailansicht:
In der Detailansicht sieht man alle Einträge einer Kategorie in einer Liste mit allen Details angezeigt, man kann sich die Einträge für einen Tag, eine Woche, ein Monat oder ein Jahr anzeigen lassen, was man einfach über einen Button auswählt und den Zeitraum kann man einfach über Pfeile ändern und über einen weiteren Button wieder zum aktuellen Datum retour kommen.
Hier hat man auch die Möglichkeit die Einträge zu bearbeiten/löschen/hinzufügen und den Namen der Kategorie zu bearbeiten. 

Wiederholung der Einträge:
Es soll auch die Möglichkeit geben Einträge zu wiederholen, Täglich, Monatlich, Quartalsweise und Jährlich, dies wird wird beim Erstellen eines neuen Eintrages so wie auch nachträglich beim Bearbeiten eines Eintrages möglich sein.
 
Statistik:
Es wird noch einen Punkt „Statistik“ geben, hier gibt es die Möglichkeit sich Statistiken für einen bestimmten Zeitraum, zu einer oder mehreren Personen und/oder dem Haushalt anzeigen zu lassen. Man kann wählen zwischen Balken- und Tortendiagramm.
2.3 Wesentliche Qualitätsanforderungen und Rahmenbedingungen
Ausgeliefert wird das Projekt in einer Zip-Datei und beinhaltet den kommentierten Quellcodes des Projekts basierend auf Java und JavaFX(Java SE 17), sowie eine ausführbare Datei für Windows Betriebssysteme. Die Daten werden in einer relationalen Datenbank gespeichert, basierend auf Derby.
3 Detaillierte Spezifikation                                                            
3.1 Akteure des Systems (Personas) 
Es gibt nur einen Endbenutzer.
3.2 Detaillierte Funktionale Anforderungen (Szenarios & Screens)
3.2.1 Einnahmen/Ausgaben Übersicht 
 
3.2.1.1 Zeitraum wählen / Wirkungsweise
	
Titel	Zeitraum wählen
Kurzbeschreibung	Zeitraum wählen über die „Übersicht“-Oberfläche. 
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Übersicht“
Beschreibung des Ablaufs (Verlauf)	Der Zeitraum ist Standartmäßig auf das aktelle Monat gestellt, um einen Zeitraum zu wählen muss auf der „Übersicht“-Oberfläche eine der Button „Monat“ oder „Jahr“ geklickt werden und über die Pfeil-Buttons neben dem Datum kann man ein Monat/Jahr weiter oder zurück springen.
Auswirkungen (Ausgabe)	Die Einstellungen werden sofort übernommen aber nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.1.2 Neue Kategorie anlegen / Wirkungsweise
	
Titel	Neue Kategorie anlegen
Kurzbeschreibung	Anlegen einer neuen Kategorie über die „Übersicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Um eine neue Kategorie anzulegen muss in der „Übersicht“-Oberfläche auf das Plus-Symbol geklickt werden.
Beschreibung des Ablaufs (Verlauf)	Dann wird zu Oberfläche „Detailansicht“ gewechselt in der man der Kategorie ihren Namen geben kann, sowie die ersten Einträge anlegen kann.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons in der „Detailansicht“ werden sämtliche Informationen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert.
Anmerkungen	
3.2.1.3 Kategorie favorisieren/entfavorisieren / Wirkungsweise
	
Titel	Kategorie favorisieren/ entfavorisieren
Kurzbeschreibung	Erlaubt es Kategorien als Favoriten zu markieren um sie in der „Favoriten“- Oberfläche zu sehen und einen Schnellzugriff möglich zu machen und sie auch wieder zu entfavoriesieren.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Der Endbenutzer muss sich auf der „Übersicht“-Oberfläche befinden.
Beschreibung des Ablaufs (Verlauf)	Durch klicken auf das Sternsymbol neben der gewünschten Kategorie, wird die Kategorie den Favoriten hinzugefügt und entfernt.
Auswirkungen (Ausgabe)	Durch erstmaliges klicken auf das Sternsymbol, wird die Kategorie den Favoriten hinzugefügt und somit in der „Favoriten“- Oberfläche sichtbar und das Sternsymbol wird ausgefüllt, durch nochmaliges klicken wird die Kategorie wieder aus den Favorietn entfernt und das Sternsymbol wieder leer.
Anmerkungen	
3.2.1.4 Kategorie bearbeiten / Wirkungsweise
	
Titel	Kategorie bearbeiten
Kurzbeschreibung	Bearbeiten einer Kategorie über die „Übersicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Man befindet sich in der „Übersicht“, dann den gewünschten Zeitraum wählen, anschließend auf das Stift-Symbol neben der gewünschten Kategorie klicken.  
Beschreibung des Ablaufs (Verlauf)	Dann wird das Fenster „Detailansicht“ geöffnet in der man den Namen der Kategorie bearbeiten, sowie die Einträge bearbeiten/löschen/anlegen kann.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons in der „Detailansicht“ werden sämtliche Informationen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert.
Anmerkungen	
3.2.1.5 Kategorie löschen / Wirkungsweise
	
Titel	Kategorie löschen
Kurzbeschreibung	Entfernen einer Kategorie über die „Übersicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Man befindet sich in der „Übersicht“, dann den gewünschten Zeitraum wählen, anschließend auf 
das X-Symbol neben der gewünschten Kategorie klicken.
Beschreibung des Ablaufs (Verlauf)	Dann öffnet sich ein Alert mit der Frage ob die Kategorie inkl. Aller Einträge für den gewählten Zeitraum wirklich gelöscht werden soll, durch bestätigen über den „OK“-Button wird die Kategorie gelöscht über „Abbrechen“-Button wird der Prozess abgebrochen und nichts gelöscht.
Auswirkungen (Ausgabe)	Durch Drücken des „OK“-Buttons werden sämtliche Daten der Kategorie zu dem betreffenden Zeitraum in der Datenbank gelöscht.
Anmerkungen	
3.2.1.6 Spezielle Abhängigkeiten / nicht funktionale Anforderungen
Keine
3.2.2 Detailansicht
  
3.2.2.1 Zeitraum wählen / Wirkungsweise
	
Titel	Zeitraum wählen
Kurzbeschreibung	Zeitraum wählen über die „Detailansicht“-Oberfläche. 
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Detailansicht“
Beschreibung des Ablaufs (Verlauf)	Der Zeitraum ist Standartmäßig auf das aktelle Monat gestellt, um einen Zeitraum zu wählen muss auf der „Detailansicht“-Oberfläche eine der Button „Tag“, „Woche“, „Monat“ oder „Jahr“ geklickt werden und über die Pfeil-Buttons neben dem Datum kann man ein Monat/Jahr weiter oder zurück springen.
Auswirkungen (Ausgabe)	Die Einstellungen werden sofort übernommen aber nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.2.2 Neuen Eintrag anlegen / Wirkungsweise
	
Titel	Neuen Eintrag anlegen
Kurzbeschreibung	Einen neuen Eintrag einer Einnahme/Ausgabe in einer Kategorie eintragen.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der  „Detailansicht“-Oberfläche.
Beschreibung des Ablaufs (Verlauf)	Dann in der Zeile über der Tabelle die Daten des neuen Eintrages(Datum, Bezeichnung, Betrag, Benutzer/Haushalt, Wiederholung ev. mit Enddatum)  eingeben.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons in der „Detailansicht“ werden sämtliche Informationen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert.
Anmerkungen	
3.2.2.3 Eintrag bearbeiten / Wirkungsweise
	
Titel	Eintrag bearbeiten
Kurzbeschreibung	Bearbeiten eines Eintrages über die „Detailansicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Den gewünschten Zeitraum wählen.
Beschreibung des Ablaufs (Verlauf)	Dann auf das Stift-Symbol neben dem gewünschten Eintrag klicken und den Eintrag direkt in der Zeile bearbeiten.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons in der „Detailansicht“ werden sämtliche Informationen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert.
Anmerkungen	
3.2.2.4 Kategorienamen bearbeiten / Wirkungsweise
	
Titel	Kategorienamen bearbeiten
Kurzbeschreibung	Bearbeiten des Kategorienamens über die „Detailansicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Um den Namen einer Kategorie zu ändern, muss das kleine Zahnrad-Symbol neben dem Kategorienamen im Tab geklickt werden.  
Beschreibung des Ablaufs (Verlauf)	Dann kann man den Namen der Kategorie bearbeiten.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons in der „Detailansicht“ werden sämtliche Informationen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert.
Anmerkungen	
3.2.2.5 Eintrag löschen / Wirkungsweise
	
Titel	Eintrag löschen
Kurzbeschreibung	Löschen eines Eintrages aus einer Kategorie über die „Detailansicht“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Den gewünschten Zeitraum wählen.
Beschreibung des Ablaufs (Verlauf)	Dann auf das X-Symbol neben dem gewünschten Eintrag klicken.  
Auswirkungen (Ausgabe)	Drücken des „Speichern“-Buttons in der „Detailansicht“ öffnet sich ein Alert mit der Frage ob der Eintrag wirklich gelöscht werden soll, durch bestätigen über den „OK“-Button werden sämtliche Änderungen in der Datenbank gespeichert. Durch Schließen des Fensters über das X oder den „Abbrechen“-Button wird nichts gespeichert und die Detailansicht bleibt geöffnet.
Anmerkungen	
3.2.2.6 Spezielle Abhängigkeiten / nicht funktionale Anforderungen
Keine
3.2.3 Favoriten
 
3.2.3.1 Zeitraum wählen / Wirkungsweise
	
Titel	Zeitraum wählen
Kurzbeschreibung	Zeitraum wählen über die „Favoriten“-Oberfläche. 
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Favoriten“
Beschreibung des Ablaufs (Verlauf)	Der Zeitraum ist Standartmäßig auf das aktelle Monat gestellt, um einen Zeitraum zu wählen muss auf der „Favoriten“-Oberfläche eine der Button „Tag“, „Woche“, „Monat“ oder „Jahr“ geklickt werden und über die Pfeil-Buttons neben dem Datum kann man ein Monat/Jahr weiter oder zurück springen.
Auswirkungen (Ausgabe)	Die Einstellungen werden sofort übernommen aber nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.3.2 Neuen Eintrag anlegen / Wirkungsweise
	
Titel	Neuen Eintrag anlegen
Kurzbeschreibung	Einen neuen Eintrag einer Einnahme/Ausgabe in einer Kategorie eintragen.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Über klicken auf den Tab mit dem Namen der gewünschten Kategorie klicken. 
Beschreibung des Ablaufs (Verlauf)	Dann in der Zeile über der Tabelle die Daten des neuen Eintrages(Datum, Bezeichnung, Betrag, Benutzer/Haushalt, Wiederholung ev. mit Enddatum)  eingeben.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons am Ende der Zeile werden sämtliche Informationen in der Datenbank gespeichert.
Anmerkungen	
3.2.3.3 Eintrag bearbeiten / Wirkungsweise
	
Titel	Eintrag bearbeiten
Kurzbeschreibung	Bearbeiten eines Eintrages über die „Favoriten“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Den gewünschten Zeitraum wählen, dann auf den Tab mit dem Namen der gewünschten Kategorie klicken.
Beschreibung des Ablaufs (Verlauf)	Auf das Stift-Symbol neben dem gewünschten Eintrag klicken und den Eintrag direkt in der Zeile bearbeiten.
Auswirkungen (Ausgabe)	Durch Drücken des „Speichern“-Buttons am Ende der Zeile werden sämtliche Informationen in der Datenbank gespeichert.
Anmerkungen	
3.2.3.4 Kategorienamen bearbeiten / Wirkungsweise
	
Titel	Kategorienamen bearbeiten
Kurzbeschreibung	Bearbeiten des Kategorienamens über die „Favoriten“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Um den Namen einer Kategorie zu ändern, muss das kleine Zahnrad-Symbol neben dem Kategorienamen im Tab geklickt werden.  
Beschreibung des Ablaufs (Verlauf)	Dann kann man den Namen der Kategorie bearbeiten.
Auswirkungen (Ausgabe)	Durch ein MouseEvent (Eine Maustaste wurde gedrückt und wieder losgelassen.) wird die Eingabe gespeichert.
Anmerkungen	
3.2.3.5 Eintrag löschen / Wirkungsweise
	
Titel	Eintrag löschen
Kurzbeschreibung	Löschen eines Eintrages aus einer Kategorie über die „Favoriten“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Den gewünschten Zeitraum wählen, dann auf den Tab mit dem Namen der gewünschten Kategorie klicken.
Beschreibung des Ablaufs (Verlauf)	Auf das X-Symbol neben dem gewünschten Eintrag klicken.  
Auswirkungen (Ausgabe)	Dann öffnet sich ein Alert mit der Frage ob der Eintrag wirklich gelöscht werden soll, durch bestätigen über den „OK“-Button wird der Eintrag gelöscht über „Abbrechen“-Button wird der Prozess abgebrochen und nichts gelöscht.
Anmerkungen	
3.2.3.6 Spezielle Abhängigkeiten / nicht funktionale Anforderungen
Keine
3.2.4 Statistiken
 
3.2.4.1 Zeitraum wählen / Wirkungsweise
	
Titel	Zeitraum wählen
Kurzbeschreibung	Zeitraum für die, die Statistik angezeigt werden soll wählen über die „Statistik“-Oberfläche. 
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Statistik“
Beschreibung des Ablaufs (Verlauf)	Der Zeitraum ist Standartmäßig auf das aktelle Monat gestellt, um einen Zeitraum zu wählen muss auf der „Statistik“-Oberfläche eine der Button „Monat“ oder „Jahr“ geklickt werden und über die Pfeil-Buttons neben dem Datum kann man ein Monat/Jahr weiter oder zurück springen.
Auswirkungen (Ausgabe)	Die Änderungen der Statistik durch Auswahl der Kriterien wird direkt angezeigt und nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.4.2 Benutzer auswählen / Wirkungsweise
	
Titel	Benutzer auswählen
Kurzbeschreibung	Benutzer für die, die Statistik angezeigt werden soll wählen über die „Statistik“-Oberfläche.
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Statistik“
Beschreibung des Ablaufs (Verlauf)	Über CheckBoxen mit dem jeweiligen Bunutzer Namen können die gewünschten Benutzer sowie der Haushalt einfach aktiviert oder deaktiviert werden und werden so mit in die Statistik aufgenommen oder nicht. Standartmäßig ist immer der Haushalt aktiviert.
Auswirkungen (Ausgabe)	Die Änderungen der Statistik durch Auswahl der Kriterien wird direkt angezeigt und nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.4.3 Diagrammart wählen / Wirkungsweise
	
Titel	Diagrammart wählen
Kurzbeschreibung	Diagrammart wählen über die „Statistik“-Oberfläche. 
Aktoren	Endbenutzer
Vorbedingungen (Eingabe)	Sie befinden sich auf der Oberfläsche „Statistik“
Beschreibung des Ablaufs (Verlauf)	Die Diagrammart kann einfach über RadioButtons ausgewählt werden. Standartmäßig ist immer eine Diagrammart aktiviert.
Auswirkungen (Ausgabe)	Die Änderungen der Statistik durch Auswahl der Kriterien wird direkt angezeigt und nicht für spätere Zwecke gespeichert.
Anmerkungen	
3.2.4.4 Spezielle Abhängigkeiten / nicht funktionale Anforderungen
Keine
3.3 Nicht-Funktionale Anforderungen
3.3.1 Vorgaben zu Hardware und Software
Für dieses Projekt wird die Java Version SE 17 und die JavaFX SDK Version 19 verwendet, als relationale Datenbank wird DERBY verwendet.

3.3.2 Performance
Keine Vorgaben
3.3.3 Resources und Anforderungen an die Hardware
Keine Vorgaben
3.3.4 Security & Safety
Keine Vorgaben
3.3.5 Reliability
Keine Vorgaben
3.3.6 Maintenance
Keine Vorgaben
3.3.7 Portability / Skalierbarkeit / Wiederverwendbarkeit
Keine Vorgaben
3.3.8 Usability
Keine Vorgaben
3.4 Schnittstellen
3.4.1 Benutzerschnittstellen (GUI)

3.4.2 Systemschnittstellen
Keine Anbindung an andere Softwareprodukte.
3.5 Systemabgrenzung, Systemarchitektur und Datenhaltung
 
3.5.1 Sequenze Digramm – Übersicht und Detailansicht
 
3.5.2 Sequenze Digramm - Favoriten
 
3.5.3 Sequenze Digramm – Statistik
 
3.5.4 ER Digramm
Schriftstil fett = PrimaryKey
Schriftstil kursiv = ForeignKey
 
 
3.5.5 Domänen spezifische Klassen
 
3.6 Rahmenbedingungen
< Hier werden Rahmenbedingungen festgelegt die das Produkt / Projekt erfüllen muss oder definitiv nicht erfüllen wird (auch die Nicht-Ziele können angegeben werden um Missverständnisse zu vermeiden). 

•	Explizit zu erfüllende Anforderungen des Auftraggebers, z.B. technologische Vorgaben, Kompatibilität (Browser!), Standards, rechtliche Vorgaben, Zeitliche Vorgaben, organisatorische Vorgaben, Vorgaben zur Geheimhaltung, etc.
•	Wichtige Produktmerkmale zur Absicherung beider Seiten, um Fehlinterpretationen und falsche Hoffnungen zu vermeiden
•	Dinge die zum Zeitpunkt dieser SRS nicht festgelegt sind und daher später Zusatzaufwand bedeuten können (nicht im Fixpreis / Lieferumfang enthalten, etc.). 
•	Geforderte Meilensteine und Liefertermine wenn direkt mit dem Produkt verbunden / dadurch bedingt (Bankomat-Software Euro Umstellung  muss am 1.1. fertig sein!>

4 Begriffsbestimmungen und Abkürzungen                                 
Begriff [alphabetisch]	Beschreibung

Die hier definierten und für diese Spezifikation gültigen Begriffe müssen in gleichem Zusammenhang und gleicher Bedeutung auch im Angebot verwendet werden. Wenn ein Lastenheft oder ein spezielles „Wording“ des Kunden vorliegt, soll dieses wenn möglich auch in allen anderen Projekt-Dokumenten verwendet werden.
                                                            
