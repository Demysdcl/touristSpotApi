import com.restapi.touristspot.domain.category.Category
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Spot(
        @Id
        val id: String? = null,
        val name: String = "",
        @DBRef
        val category: Category = Category(),
        val location: Array<Double> = emptyArray()
) {
    override fun equals(other: Any?) = this === other || (other is Spot
            && name == other.name
            && category == other.category
            && location.contentEquals(other.location))

    override fun hashCode() = 31 * (name.hashCode() + category.hashCode() + location.contentHashCode())

}