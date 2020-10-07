package energy.uniper.futuristiccarparkspringboot.service

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.InterfaceLevel
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.stereotype.Component
import kotlin.math.floor
import kotlin.math.max
import kotlin.random.Random

@Component
class CarParkService(val carRepository: CarRepository) {

	private val levels = mutableListOf<InterfaceLevel>()
	private val calculateLevel = {maxLevels: Int, value: Double -> max((maxLevels - floor(value / 10_000)).toInt(),0)}
	
	init {
		for (i in 0..9){
			levels.add(Level(i))
		}
	}

	fun parkCar() {
		val carList = carRepository.findAll()
		for (car in carList) {
			getParkingLot(car)
			if (car.status == CarStatus.NOSLOTS) {
				println("${car.idPlate} could not be parked, because Carpark has no lots available: ${car.value}")
			}
		}
		carRepository.saveAll(carList)
	}
	
	fun removeCarAndGetFee(car: Car): Double{
		val level = car.level!!
		car.status = CarStatus.LEFT
		carRepository.save(car)
		
		return levels[level].removeCarAndCalculateFee(car)
	}

	fun getParkingLot(car: Car): CarStatus {
		val level = getLevel(car)
		for (targetLevel in level..9) {
			if (levels[targetLevel].isParkinglotAvailable) {
				levels[targetLevel].parkCar(car)
				setControl(car, calculateLevel(10, car.value!!))
				car.status = CarStatus.PARKED
				return CarStatus.PARKED
			}
		}
		car.status = CarStatus.NOSLOTS
		return CarStatus.NOSLOTS
	}
	
	fun getLevel(car: Car): Int {
		if (car.isPartyMember!!) return 0
		return calculateLevel(10, car.value!!)
	}
	
	 fun setControl(car: Car, actualLevel: Int) {
		if (car.isPartyMember!!){
			car.toControll = false
			return
		}
		
		if (actualLevel < 5)
			car.toControll = 0.01 * actualLevel > Random.nextDouble(0.0, 1.0)
		else
			car.toControll = 0.1 * actualLevel > Random.nextDouble(0.0, 1.0)
	}
}