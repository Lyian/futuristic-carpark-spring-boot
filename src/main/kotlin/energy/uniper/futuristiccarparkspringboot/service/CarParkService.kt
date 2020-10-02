package energy.uniper.futuristiccarparkspringboot.service

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class CarParkService(val carRepository: CarRepository) {
	val allLevels = mutableListOf<Level>()

	fun parkCar(){
		val carList = carRepository.findAll()
		initLevels(carList)
		carRepository.saveAll(carList)
	}

	fun initLevels(carList: MutableIterable<Car>) {
		for (i in 0..10){
			allLevels.add(i, Level(i, carRepository))
		}
		var level = 0
		for (car in carList) {
			if(car.status == CarStatus.REQUESTING) {
				level = getActualLevel(car)
				if(level<11 && !allLevels[level].levelIsFull()){
					addCarToLevel(car, level)
				}
				else {
					car.status = CarStatus.NOSLOTS
					println("Auto kann nicht abgestellt werden")
				}
			}
		}
	}

	fun addCarToLevel(car : Car, level : Int) {
		allLevels[level].addCar(car)
	}



	fun fetchCar(car : Car){
		for (i in getDesiredLevel(car)..10){
			if(allLevels[i].carIsInLevel(car)){
				println(" ${car.idPlate} ${allLevels[i].calculatePriceForLevel(car)}")
				allLevels[i].removeCar(car)
				break;
			}
		}
	}

	fun getDesiredLevel(car : Car) : Int {
		if (car.isPartyMember!!) {return 0}
		var level = 10 - Math.floor(car.value!! / 100000 * 10)
		if (level > 10) {level = 10.0}
		if (level < 0) {level = 0.0}
		return level.toInt()
	}


	fun getActualLevel(car : Car) : Int {

		for (i in getDesiredLevel(car)..10) {
			if (!allLevels[i].levelIsFull()) {
				return i
			}
		}
		return 111
	}


}