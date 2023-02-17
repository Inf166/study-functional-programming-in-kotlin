
class Thermometer( private var meinSensor : Sensor) {

    val observerList = ArrayList<TemperatureObserver>()         // Observerliste als ArrayList initialisieren

    fun startChecking (n : Int ) {                    // läuft die Schleife n mal durch

        for ( x in 0..n ){

            // println ("@Thermometer misst: ${newValue} Grad")

            informObserver( meinSensor.getTemperature() )   // ruft get Temp auf und informiert die Observer
                                                            // Es gibt auch eine Funktion für Kotlin
                                                            // Aber zum üben und probieren hier nicht genutzt
        }
    }

    // Fügt der Observerliste einen neuen Observer hinzu
    fun addObserver( newObserver : TemperatureObserver ){
        observerList.add(newObserver)
    }

    // Informiert die Oberserver über den neuen Wert von getTemp
    fun informObserver( newValue : Float ){
        observerList.forEach{
            it.update( newValue )                   // IT steht für Iterator - durchläuft die Liste und repräsentiert
        }                                           // das aktuell betrachtete Objekt in der Liste
    }
}