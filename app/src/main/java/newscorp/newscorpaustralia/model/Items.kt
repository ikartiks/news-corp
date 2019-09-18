package newscorp.newscorpaustralia.model

class Items {
    lateinit var standFirst: String

    var correctAnswerIndex: Int =0

    lateinit var imageUrl: String

    lateinit var storyUrl: String

    lateinit var headlines: ArrayList<String>

    lateinit var section: String

    override fun toString(): String {
        return "ClassPojo [standFirst = $standFirst, correctAnswerIndex = $correctAnswerIndex, imageUrl = $imageUrl, storyUrl = $storyUrl, headlines = $headlines, section = $section]"
    }
}
