import kotlin.collections.List

fun <T,R > map (a : Array<T>, transform: (T)->R ) : Array<R> {
    val neuesArray = arrayOfNulls<Any>(a.size)
    var j = 0
    while (a.size >= j) {
        neuesArray[j] = transform(a[j]);
    }

    return neuesArray as Array<R>
}

fun <T> reduce (a : Array<T>, f : (T) -> T) {
    val neuesArray = arrayOfNulls<Any>(a.size)
    var j = 0
    var ergebnis
    while (a.size >= j) {
        ergebnis = f(a[j]);
    }

    return ergebnis as <T>
}

fun <T,R> map_reduce (a : Array<T>, transform : (T)->R , operator : (T)->R) {

}
fun main (  ) {

}