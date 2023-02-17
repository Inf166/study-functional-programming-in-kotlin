// Aufgabe 2 a)

// Mappt eine Liste mit Werten vom Typ T auf eine neue Liste mit Werten vom Typ R.
// Die Funktion f legt dabei die Mapping-Regel fest.
fun <T, R> map(list: List<T>, f: (T) -> R): List<R> = when (list) {
    is List.Nil -> List()
    is List.Node<T> -> List.Node<R>(f(list.head), map(list.tail, f))
}

fun <T, R> mapDurchIteration(list: List<T>, f: (T) -> R): List<R> {

    var neueListe = List.Nil as List<R>
    var alteListe = list

    while (alteListe != List.Nil) {
        when (alteListe) {
            is List.Nil -> neueListe
            is List.Node<T> -> {
                neueListe = neueListe.addFirst(f(alteListe.head))
                alteListe = alteListe.removeFirst()
            }
        }
    }

    return neueListe
}

fun <T, R> mapDurchFold(list: List<T>, f: (T) -> R): List<R> =
    fold(list, List(), { neueListe, head -> neueListe.addFirst(f(head)) })

// Erzeugt eine neue Liste, in der Werte ersetzt werden, wenn die Bedingungsfunktion p erfüllt ist (p steht für Prädikat).
// Die Funktion f legt fest, wie ein Wert ersetzt wird.
// Die Werte der neuen und alten Liste sind jeweils vom selben Typ
// (z.B. ein Int Wert wird durch einen neuen Int Wert ersetzt.)
fun <T> replaceIf(list: List<T>, f: (T) -> T, p: (T) -> Boolean): List<T> = when (list) {
    is List.Nil -> List.Nil as List<T>
    is List.Node<T> ->
        if (p(list.head))
            List.Node<T>(f(list.head), replaceIf(list.tail, f, p))
        else
            List.Node<T>(list.head, replaceIf(list.tail, f, p))
}

// Erzeugt eine neue Liste, in der alle Werte herausgefiltert werden, auf die die Bedingungsfunktion p zutrifft.
fun <T> filter(list: List<T>, p: (T) -> Boolean): List<T> = when (list) {
    is List.Nil -> List.Nil as List<T>
    is List.Node<T> ->
        if (p(list.head))
            List.Node<T>(list.head, filter(list.tail, p))
        else
            filter(list.tail, p)
}

// Liefert true, wenn die Bedinungsfunktion p für mind. einen Werte der Liste zutrifft.
fun <T> any(list: List<T>, p: (T) -> Boolean): Boolean = when (list) {
    is List.Nil -> false
    is List.Node<T> -> p(list.head) || any(list.tail, p)
}

fun <T> all(list: List<T>, p: (T) -> Boolean): Boolean = when (list) {
    is List.Nil -> false
    is List.Node<T> -> p(list.head) && all(list.tail, p)
}

// Verknüpft jeweils das Ergebnis der letzten Operation mit dem nächsten Element der Liste.
// Es wird mit einem Startwert accumulated begonnen.
// Dieser Wert soll gemeinsam mit dem nächsten Element der Liste durch die Funktion f zu einem neuen Wert verknüpft werden.
// Das Ergebnis dieser Operation ist dann wieder das accumulated-Argument für den nächsten rekursiven Aufruf.
fun <T, R> fold(list: List<T>, accumulated: R, f: (R, T) -> R): R = when (list) {
    is List.Nil -> accumulated
    is List.Node<T> -> fold(list.tail, f(accumulated, list.head), f)
}

// Aufgabe 2 b)
class Product(var productName: String, var price: Double, var rating: Int)

fun main() {
    val burger = Product("Burger", 10.50, 4)
    val fries = Product("Pommes Frites", 3.90, 5)
    val cola = Product("Coca Cola", 2.90, 3)
    val shake = Product("Vanilla Shake", 2.30, 5)
    val nuggets = Product("Chicken Mc Nuggets", 7.90, 2)

    val produkte = List(burger, fries, cola, shake, nuggets)


    println("Aufgabe 2 b.1")
    val produkteMitNurNamen = map(produkte, { it.productName })
    println(produkteMitNurNamen)
    val produkteMitNurNamenmapDurchFold = mapDurchFold(produkte, { it.productName })
    println(produkteMitNurNamenmapDurchFold)
    val produkteMitNurNamenmapDurchIteration = mapDurchIteration(produkte, { it.productName })
    println(produkteMitNurNamenmapDurchIteration)
    println(" ")


    println("Aufgabe 2 b.2")
    val produkteDieTeuererSindAlsZehnEuro = map(filter(produkte, { it.price > 10 }), { it.productName })
    println(produkteDieTeuererSindAlsZehnEuro)
    println(" ")

    println("Aufgabe 2 b.3")
    val produkteDieJetztNeunzehnProzentTeurerSindPunktPunktPunktWasEinName = map(produkte, { it.price * 1.19 })
    println(produkteDieJetztNeunzehnProzentTeurerSindPunktPunktPunktWasEinName)
    println(" ")

    println("Aufgabe 2 b.4")
    val produkteDieMindestensDreiSterneHaben = map(filter(produkte, { it.rating >= 3 }), { it.productName })
    println(produkteDieMindestensDreiSterneHaben)
    println(" ")

//    val produktliste = ArrayList<String>("")
    println("Aufgabe 2 b.5")
    val produkteWoAOderBAnfangIstIstJetztAngebot = map(replaceIf(produkte,
        { it: Product -> Product("Angebot: ${it.productName}", it.price, it.rating) },
        { it.productName.startsWith("A") || it.productName.startsWith("B") }), { it.productName })
    println(produkteWoAOderBAnfangIstIstJetztAngebot)
    println(" ")

    println("Aufgabe 2 b.6")
    val gibtEsProdukteUeber100Euro = any(produkte, { it.price > 100 })
    println(gibtEsProdukteUeber100Euro)
    println(" ")

    println("Aufgabe 2 b.7")
    val summeAllerProdukte = fold(produkte, 0.00, { acc: Double, iterator: Product -> acc + iterator.price })
    println(summeAllerProdukte)
    println(" ")

    println("Aufgabe 2 b.8")
    val maxPreisDerProdukte =
        fold(produkte, 0.00, { acc: Double, iterator: Product -> if (acc < iterator.price) iterator.price else acc })
    println(maxPreisDerProdukte)
    println(" ")

    println("Aufgabe 3")
    fun productFactory(produktName: String, rating: Int): (Double) -> Product =
        { price: Double -> Product(produktName, price, rating) }

    val kaeseFactory = productFactory("Käse", 5)
    val teurerKaese = kaeseFactory(4.99)
    val billigerKaese = kaeseFactory(0.99)
    println("OMG wie billig: ${billigerKaese.productName} für nur ${billigerKaese.price}€!!!")
}
