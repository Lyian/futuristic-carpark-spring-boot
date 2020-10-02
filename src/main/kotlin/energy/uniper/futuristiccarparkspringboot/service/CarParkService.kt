package energy.uniper.futuristiccarparkspringboot.service

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.CarPark
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.stereotype.Component
import java.util.*
import kotlin.random.Random

@Component
class CarParkService(val carRepository: CarRepository) {

	val carPark = CarPark()

	fun parkCar(){
		val carList = carRepository.findAll()

		for(car in carList){
			val levelOfCurrentCar = determineLevel(car)
			if(levelOfCurrentCar < carPark.levels.size){
				carPark.levels[levelOfCurrentCar].addCar(car)
			} else {
				car.status = CarStatus.NOSLOTS
				//TODO Auto kann nicht geparkt werden
				println("Voll!!")
			}
		}

		carRepository.saveAll(carList)
	}

	fun determineLevel(car: Car) : Int{
		var level: Int = 99


		if (car.value!! > 100000 || car.isPartyMember!!){
			level = 0
		} else{
			level = ((10 - kotlin.math.floor(car.value / 10000)).toInt())
		}
		val initialLevel = level

		while (carPark.levels[level].isLevelFull()) {

			if(level < carPark.levels.size){
				level += 1
			} else {
				return 99
			}

		}

		markCarForControl(car, initialLevel, level)

		return level
	}

	fun markCarForControl(car: Car, initialLevel: Int, level: Int){

		val eps = Random.nextDouble(0.0,1.0)

		if(level == 0 && !car.isPartyMember!! && eps <= 0.001){
			car.toControll = true
		} else car.toControll = initialLevel > 4 && eps <= 0.01 * level

	}
	
	fun removeCar(car: Car){

		carPark.levels[car.level!!].listParkinglots.remove(car)
		car.leftAt = car.enteredAt?.plusHours(Random.nextLong(1,24))
		val priceForCar = carPark.levels[car.level!!].calculatePriceForLevel(car)
		println(car.idPlate + " muss " + priceForCar + "€ bezahlen.")
		car.status = CarStatus.LEFT
	}
}

























