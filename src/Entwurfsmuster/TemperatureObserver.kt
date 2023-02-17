
// Observer lagern Anwendungslogik aus was passieren soll 'wenn' und können für verschiedene Thermometer genuzt werden.
// eher gesagt die Auswertung

// Gerippe für die Temp.Observer (Interface)
interface TemperatureObserver {

    fun update( newValue : Float )          // Temperature Observer sollen beim call der Updatefunktion etwas damit machen
}

// Liefert 100 gemessene Werte
class WeatherReport : TemperatureObserver {

    val valueList = ArrayList<Float>()          // Die Liste mit den Werten
    var counter = 0                             // Der Zähler für die 100 Werte

    override fun update( newValue : Float ) {
        counter++                               // Bei jedem Update wird der Zähler um 1 erhöht
        valueList.add(newValue)                 // und der Liste der Wert hinzugefügt
        if ( counter == 100 ) {                 // Erreicht die Methode 100
            print("Es wurden die Werte: ")          // Werden die Werte auf der Console ausgegeben
            valueList.forEach{                      // Für jeden Wert in der Liste
                print("${it}, ")                        // gib ES aus
            }
            print(" gemessen.")
            println(" ")                            // kleine neue Leere Zeile für die Lesbarkeit
            valueList.clear()                       // Die Liste wird freigemacht
            counter = 0                             // und der Zähler zurückgesetzt
        }
    }
}

// Wenn ein bestimmter Wert erreicht wurde soll ein Alarm ausgegeben werden
class TemperatureAlert : TemperatureObserver {
    val notifyValue = 37F                           // Der zuerreichende Wert

    override fun update( newValue : Float ) {

        if ( newValue > notifyValue )               // Wenn der gemessene Wert über dem Alarmwert liegt
            println("Ganz schön heiß hier!")        // Melde das auf der Konsole

    }
}

// Entscheidet wann geheizt werden soll
class HeatingSystem() : TemperatureObserver {
    constructor(pmyHeatingStrategy : HeatingStrategy) : this() {        // Alternativer Konstruktor für Faule Boys & Girls
        myHeatingStrategy = pmyHeatingStrategy
    }

    val valueList = ArrayList<Float>()                                  // Liste mit gemessenen Werten
    var counter = 0                                                     // Zähler
    var myHeatingStrategy : HeatingStrategy = ReasonableHeatingStrategy()   // Standard Strategie fürs Heizen
    var isalreadyon : Boolean = false                                   // Variable ob die Heizung schon an ist

    override fun update( newValue : Float ) {
        counter++                                                       // Zähler um 1 erhöhen
        valueList.add(newValue)                                         // Wert der Liste hinzufügen
        if ( (counter >= 10) ) {                                        // Werden 10 gemessene Werte erreicht
            if(myHeatingStrategy.needsHeating(valueList.subList(valueList.size-10,valueList.size))){        // Es wird eine Subliste der letzten 10 Einträge ausgewertet von der Strategie
                if(!isalreadyon)println("@Heizung AN")                  // Der Rückgabewert entscheidet ob die Heizung an
                isalreadyon = true
            } else {
                if(isalreadyon)println("@Heizung AUS")                  // oder ausgestellt wird
                isalreadyon = false                                     // Ausgabe erfolgt nur bei einer Änderung weil sonst SPAM
            }
//            valueList.clear() // Ja der Speicher läuft iwann voll aber für die Auswertung vieler Daten eigentlich geil #BigData
//            counter = 0       // Könnte man aber besser machen, da die Werte immer hinzugefügt werden und nie gelöscht
        }
    }

    fun addStrategy( HeatingStrategy : HeatingStrategy ) {
        myHeatingStrategy = HeatingStrategy                 // fügt eine Strategie dem System hinzu
    }
}