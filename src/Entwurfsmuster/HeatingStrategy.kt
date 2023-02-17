
// Strategien lagern Anwendungslogik aus und können für verschiedene Thermometer genuzt werden.
// #WenigerCodeIstMehr

// Gerippe Inteface für die Strategien
interface HeatingStrategy {

    fun needsHeating( last10measurements : List<Float> ) : Boolean

}

// Wenn der lezte Gemessene Wert unter 19 liegt dann gibt true aus sonst halt false
class InstantHeatingStrategy : HeatingStrategy {

    override fun needsHeating( last10measurements : List<Float> ) : Boolean {
        println("Powered by InstantHeatingStrategy")                // für übersichtlichkeit und nachvollziehbarkeit
        return last10measurements.last()<19
    }
}

class SensibleHeatingStrategy : HeatingStrategy {

    override fun needsHeating( last10measurements : List<Float> ) : Boolean {
        println("Powered by SensibleHeatingStrategy")
        last10measurements.forEach{             // wenn einer der lezten 10 unter 20 liegt dann gibt true aus

            if( it < (20).toFloat() ) return true

        }

        return false                            // sonst halt false

    }
}

class ReasonableHeatingStrategy : HeatingStrategy {

    override fun needsHeating( last10measurements : List<Float> ) : Boolean {
        println("Powered by ReasonableHeatingStrategy")
        var counter = 0                                 // Zähler für die Werte unter 19

        last10measurements.forEach{
            if( it <= (15).toFloat() ) return true      // Wenn gemessener Wert der lezten 10 unter 15
            if( it <= (19).toFloat() ) counter++        // oder 5 der Werte unter 19 sind
        }
        return (counter >= 5)                           // dann gibt wahr aus
    }
}