
class Person (val vorname : String, val name : String,
              val alter : Int)

fun hallo () {
    println("Hallo")
}

fun moin() {
    println("Moin")
}

fun main () {
    val ausgabe  =  :: moin
    ausgabe ()

    val ausgabe2 = { println("Na sowas.") }
    ausgabe2()

    val values = listOf (4,23,2,5,800)


    val larger100 : Boolean =  values.any ({ x : Int -> x > 100 })
    println (larger100)

    val values2 = values.filter{ it > 20} .map  {
        if (it>10) it * 1.19 else it * 1.07 }


    println(values2)

    val cities = listOf<String>("Gummersbach" ,"Köln" , "Hamburg",
        "Kiel", "München")

    val sortedCities = cities.sortedBy { it.length }
    println(sortedCities)

    val stadtMitK = cities.filter { it.startsWith("K") }
    println (stadtMitK)


    val personas = listOf(Person("Hanna" , "Heiter" , 19) ,
        Person ("Lars" , "Lustig" , 22) ,
        Person ("Fridolin" , "Fröhlich" ,13),
        Person ("Susi" , "Sonnenschein",12))

    val volljaehring = personas.filter { it.alter >= 18 }


    val values3 = listOf (5,8,7,6)
    val summe = values3.fold(0 , { acc :Int , curr: Int -> acc+curr} )

    val produkt = values3.fold ( 1 , {acc, curr -> acc*curr} )
    println (summe)
    println (produkt)

    val youngest = personas.fold ( personas[0] ,
        { acc, curr -> if (curr.alter < acc.alter ) curr else acc}  )
    println (youngest.vorname)
}