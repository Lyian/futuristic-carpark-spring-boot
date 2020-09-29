package energy.uniper.futuristiccarparkspringboot.model


import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Car{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null
	val brand: String? = null
	val type: String? = null
	val idPlate: String? = null
	val owner: String? = null
	val value: Double? = null
	val isPartyMember: Boolean? = null
	var toControll: Boolean? = null
	var status: String? = null
	val enteredAt : LocalDateTime? = null
	var leftAt : LocalDateTime? = null
}