package energy.uniper.futuristiccarparkspringboot.service

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.stereotype.Component

@Component
class CarParkService(val carRepository: CarRepository) {

	var levels = mutableListOf<Level>()

	init {
		for (i in 0..10){
			levels.add(Level(i))
		}
	}

	fun parkCar() {
		val carList = carRepository.findAll()
		for (targetCar in carList) {
			getParkingLot(targetCar)
			if (targetCar.status == CarStatus.NOSLOTS) {
				println(targetCar.idPlate)
			}
			carRepository.saveAll(carList)
		}
	}

	fun getParkingLot(car: Car): CarStatus {
		val wishLevel = chooseLevel(car)
		for (targetLevel in wishLevel..10) {
			if (levels[targetLevel].parkingLotFree()) {
				levels[targetLevel].parkCar(car)
				car.status = CarStatus.PARKED
				return CarStatus.PARKED
			}
		}
		car.status = CarStatus.NOSLOTS
		return CarStatus.NOSLOTS
	}




	fun chooseLevel(car: Car): Int {
		if (car.isPartyMember!!) {return 0}
		var targetLevel = 10 - Math.floor(car.value!! / 100000 * 10)
		if (targetLevel > 10) {targetLevel = 10.0}
		if (targetLevel < 0) {targetLevel = 0.0}
		return targetLevel.toInt()
	}



	fun payParkingLot(car: Car): Double{
		val wunschebene = chooseLevel(car)
		var price = 0.0
		for (zielebene in wunschebene..10) {
			if (levels[zielebene].carIsInLevel(car)) {
				price = levels[zielebene].removeCar(car)
				car.status = CarStatus.LEFT
				carRepository.save(car)
			}
		}
		return price
	}

}