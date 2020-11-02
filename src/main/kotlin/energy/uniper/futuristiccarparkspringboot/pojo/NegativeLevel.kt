package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.model.Car
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.sqrt
import kotlin.random.Random

class NegativeLevel (private val levelId: Int):InterfaceLevel{
	private val listParkinglots = mutableListOf<Car>()
	override val isParkinglotAvailable = listParkinglots.count() < 20
	
	override fun parkCar(car: Car) {
		addCar(car)
		car.enteredAt = LocalDateTime.now()
		car.level = levelId
		sortAlphabetic()
	}
	
	override fun removeCarAndCalculateFee(car: Car): Double {
		val price = calculatePriceForLevel(car)
		listParkinglots.remove(car)
		
		println("Price for ${car.idPlate} is ${price}")
		return price
	}
	
	private fun calculatePriceForLevel(car: Car) : Double {
		car.leftAt = LocalDateTime.now().plusHours(Random.nextLong(0, 10))
		return Duration.between(car.enteredAt, car.leftAt).toHours() * sqrt(levelId.toDouble()) * 0.01
	}
	
	private fun addCar(car: Car){
		listParkinglots.add(car)
	}
	
	private fun sortAlphabetic() {
		listParkinglots.sortedBy{it.idPlate}
	}
}