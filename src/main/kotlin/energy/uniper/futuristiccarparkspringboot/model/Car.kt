package energy.uniper.futuristiccarparkspringboot.model


import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Car{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null
	val idPlate: String? = null
	val owner: String? = null
	val value: Double? = null
	val isPartyMember: Boolean? = null
	var toControll: Boolean? = null
	@Enumerated(EnumType.STRING)
	var status: CarStatus? = null
	var level: Int? = null
	val enteredAt : LocalDateTime? = null
	var leftAt : LocalDateTime? = null
}