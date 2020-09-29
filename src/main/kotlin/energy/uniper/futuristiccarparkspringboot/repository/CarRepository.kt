package energy.uniper.futuristiccarparkspringboot.repository

import energy.uniper.futuristiccarparkspringboot.model.Car
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CarRepository : CrudRepository<Car, Long>{
	fun findById(id: Int) : Optional<Car>
}