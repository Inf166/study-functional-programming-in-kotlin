// Kotlin Libaries einbinden (Runden und Random Klassen)
import kotlin.math.roundToInt
import kotlin.random.Random

// Vorteile von Dekorierern:
// Keine Klassenexplosion (unnötig viele Klassen erstellen für jede Kombination der Dekorierer)
// #UndWenigerCode/KOT


// Interface Gerippe für die Sensoren
interface Sensor {

    // Funktion für Werteabfrage
    fun getTemperature() : Float
}

// Liefert zufällige Werte in einem vordefinierten Wertebereich
// Implementiert das Interface Sensor
class RandomSensor : Sensor {

    private val min = -10   // Unveränderliche Variable
    private val max = 45    // Könnte man aber auch in den Parametern machen

    override fun getTemperature(): Float {
        return Random.nextFloat() + Random.nextInt(min,max)
    }// Liefert eine Kommazahl zwischen 0 & 1 plus einer Ganzahl zwischen min und max

}
// Liefert einen Wert der Schwankt um relle Raumtemperaturschwankungen zu simulieren
// Implementiert das Interface Sensor
class UpDownSensor() : Sensor {

    private var aktuelleTemperatur : Float = 22.0F // Anfangswert

    override fun getTemperature(): Float {
        var schwankung : Float = Random.nextFloat()                 // Simuliert eine geringfügige Schwankung (0-1)
        var schwankungCause : Int = Random.nextInt(1, 4) // Würfelt eine Zahl (1 2 oder 3)

        when (schwankungCause) {                    // Wenn 1 2 oder 3 dann
            1 -> aktuelleTemperatur += schwankung   // Schwankung zu dem Anfangswert addieren und überschreiben
            2 -> aktuelleTemperatur -= schwankung   // oder abziehen
            3 -> aktuelleTemperatur                 // oder nix machen
            else -> {                               // Ja oder halt Fehler, sollte aber nicht passieren :D
                print("Fehler: SchwankungCause liefert falsche Werte")
            }
        }

        return aktuelleTemperatur;                  // Dann gib den ganzen Spaß auch noch zurück
    }
}

// Dekorierter Sensor ist Abstrakt um schon mal einen Sensor als Parameter zu haben (auch nicht implementiert)
abstract class DekoriererSensor(pmeinSensor : Sensor) : Sensor { // Der Dekorierte Sensor ist ein Sensor mit Parameter
                                                                 // Als Parameter bekommt er einen Sensor
    open var meinSensor : Sensor = pmeinSensor                   // Den speichert er unter meinSensor
                                                                 // Im Weiteren Verlauf als Supersensor beschrieben
    override fun getTemperature(): Float {               // Wird jetzt als getTemp aufgerufen
        return meinSensor.getTemperature()               // So kann der Dekorierer das Ergebnis vom Super Sensor dekorieren
    }
}

// implementiert DekoriererSensor und überschreibt den übergebenen Sensor mit einem eigenen
class RoundValues(override var meinSensor: Sensor) : DekoriererSensor(meinSensor) {

    override fun getTemperature(): Float {
        return super.getTemperature().roundToInt().toFloat() // rundet das Ergebnis von MeinSensor für getTemp
    }                                                                  // wandelt aber um in Float für die Rückgabe
                                                                // Super meint den Sensor von der DekoriererSensor Klasse
                                                                // Diese ist Abstrakt und man kann so auf meinSensor referenzieren/ansprechen
}

// Liefert nur Werte die im Wertebereich liegen
class SensorLimits(override var meinSensor: Sensor) : DekoriererSensor(meinSensor) {

    private val min = -5   // Wertebereich
    private val max = 45    // Private weil niemand anders das wissen muss von den Klassen

    override fun getTemperature(): Float {

        var matchingSensorValue : Float = super.getTemperature() // Erster Wert von Super Sensor abfragen

        while (matchingSensorValue > max || matchingSensorValue < min) {   // Solange der Wert nicht ins Intervall passt
            matchingSensorValue = super.getTemperature()                   // Überschreibe den gemessenen Wert
        }                                                                  // bis er passt

        return matchingSensorValue                                              // Rückgabe des passenden Werts
    }

}

// Loggt halt den Wert auf der Konsole
class SensorLogger(override var meinSensor: Sensor) : DekoriererSensor(meinSensor) {

    override fun getTemperature(): Float {
        var log = super.getTemperature()                                // Von super Sensor den Wert abfragen
        println("@SensorLogger: Die Temperatur beträgt: $log Grad.")    // Und auf der Konsole loggen
        return log                                                      // Und weitergeben an die oberklasse
    }
}

// Soll die Dublikate ignorieren
class IgnoreDuplicates(override var meinSensor: Sensor) : DekoriererSensor(meinSensor) {

//    private var oldValue = this.zuIgnorierndeWerteSensor.getTemperature()
    private var oldValue = 0.0F

    override fun getTemperature(): Float {

        var matchingSensorValue : Float = super.getTemperature()    // fragt den Wert ab

        while (((matchingSensorValue*100).roundToInt())/100 == ((oldValue*100).roundToInt())/100 ) {    // prüft ob Werte gleich auf 2 Nachkomma stellen
            matchingSensorValue = super.getTemperature()                                                // falls gleich, mach nochmal
        }
        oldValue = matchingSensorValue      // alten wert mit neuem überschreiben wenns passt
        return matchingSensorValue          // und zurückgeben
    }

}