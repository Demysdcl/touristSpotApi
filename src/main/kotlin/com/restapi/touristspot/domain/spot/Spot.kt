import com.restapi.touristspot.domain.category.Category
import com.restapi.touristspot.domain.user.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Spot")
data class Spot(
        @Id
        val id: String? = null,
        val name: String = "",
        val location: Array<Double> = emptyArray(),

        @DBRef
        val category: Category = Category(),

        @DBRef
        val createBy: User = User()


) {
    override fun equals(other: Any?) = this === other || (other is Spot
            && name == other.name
            && category == other.category
            && location.contentEquals(other.location))

    override fun hashCode() = 31 * (name.hashCode() + category.hashCode() + location.contentHashCode())

}