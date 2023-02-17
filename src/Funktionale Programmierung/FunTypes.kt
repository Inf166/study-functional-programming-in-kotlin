

fun doSomething (str : String , f: (String) -> Int ) : Int {
    return f(str)
}

val stringLaenge : (String) -> Int = { s: String -> s.length }

val addition : (Int, Int) -> Int = { x:Int , y:Int -> x + y  }

fun createLimit (  limit : Int ) : (Int) -> Int  {
    val limLambda = { wert : Int -> if (wert < limit) wert else limit}
    return limLambda

}

fun createLimit2 (  limit : Int )  =
      { wert: Int -> if (wert < limit) wert else limit }





fun main () {
    println("Types")
    val r = doSomething("hallo" , stringLaenge )

    val l = createLimit(200)

//    println (createLimit (99) (32)   )
    // println (limit (122))

}