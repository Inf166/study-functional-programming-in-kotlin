
fun main (  ) {
    // Theromometer erzeugen mit Sensoren und Dekorierern
    val thermometerOG = Thermometer( RoundValues( UpDownSensor() ) )
    val thermometer1 = Thermometer( IgnoreDuplicates( RandomSensor() ) )
    val thermometer2 = Thermometer( RoundValues( IgnoreDuplicates( SensorLogger(UpDownSensor()) ) ) )
    val thermometer3 = Thermometer( SensorLogger( RandomSensor() ) )




    // Dem Thermometer Observer hinzufügen
    thermometerOG.addObserver( WeatherReport() )
    thermometer1.addObserver( TemperatureAlert() )
    thermometer2.addObserver( TemperatureAlert() )
    thermometer3.addObserver( HeatingSystem() )

    // Thermometer Werte Prüfen lassen
    thermometerOG.startChecking(1000)
    println("\n")                               // Kleiner Break für Lesbarkeit
    thermometer1.startChecking(50)
    println("\n")                               // Kleiner Break für Lesbarkeit
    thermometer2.startChecking(50)
    println("\n")                               // Kleiner Break für Lesbarkeit
    thermometer3.startChecking(50)
    println("\n")                               // Kleiner Break für Lesbarkeit

}