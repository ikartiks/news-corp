package newscorp.newscorpaustralia.model

class Game {
    lateinit var product: String

    lateinit var items: ArrayList<Items>

    lateinit var resultSize: String

    lateinit var version: String

    override fun toString(): String {
        return "ClassPojo [product = $product, items = $items, resultSize = $resultSize, version = $version]"
    }
}
