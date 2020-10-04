package energy.uniper.futuristiccarparkspringboot.model


import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Car{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
	var idPlate: String? = null
	var owner: String? = null
	var value: Double? = null
	var isPartyMember: Boolean? = null
	var toControll: Boolean? = null
	@Enumerated(EnumType.STRING)
	var status: CarStatus? = null
	var level: Int? = null
	var enteredAt : LocalDateTime? = null
	var leftAt : LocalDateTime? = null
}