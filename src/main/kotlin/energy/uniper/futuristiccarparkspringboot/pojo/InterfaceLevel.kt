package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.model.Car
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.random.Random

interface InterfaceLevel{
	val isParkinglotAvailable: Boolean
	
	fun parkCar(car: Car)
	fun removeCarAndCalculateFee(car: Car) : Double
}