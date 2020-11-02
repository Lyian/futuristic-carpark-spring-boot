package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.model.Car

interface InterfaceLevel{
	val isParkinglotAvailable: Boolean
	fun parkCar(car: Car)
	fun removeCarAndCalculateFee(car: Car) : Double
}