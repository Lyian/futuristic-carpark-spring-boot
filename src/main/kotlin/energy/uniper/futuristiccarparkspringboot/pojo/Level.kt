package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import java.time.LocalDateTime
import kotlin.math.sqrt
import kotlin.random.Random

class Level (val levelId: Int, val carRepository: CarRepository){
	// Die Position in der Liste gibt die id des Parkplatzes an.
	val listParkinglots = mutableListOf<Car>()
	
	fun calculatePriceForLevel(car: Car) : Double {
		if(car.status == CarStatus.PARKED){
			val hoursParked = car.leftAt?.hour?.minus(car.enteredAt!!.hour)
			if (hoursParked != null) {
				return hoursParked * 1.0 * sqrt(levelId.toDouble())
			}
		}
		return 0.0
	}
	
	fun addCar(car: Car){
		car.level = levelId
		car.enteredAt = LocalDateTime.now()
		car.status = CarStatus.PARKED
		car.toControll = getControl(car)
		listParkinglots.add(car)
		reorderCars()
		persistCarLevel(car)
	}


	fun getControl(car : Car) : Boolean {
		if(car.level == 0 && !car.isPartyMember!!){
			return 0.01 > Random.nextDouble(0.0, 1.0)
		}
		else if(levelId > 4){
			return 0.01 * levelId > Random.nextDouble(0.0, 1.0)
		}
		return false
	}


	fun removeCar(car : Car) {
		car.leftAt = LocalDateTime.now()
		car.status = CarStatus.LEFT
		listParkinglots.remove(car)
		reorderCars()
		persistCarLevel(car)
	}

	fun persistCarLevel(car : Car){
		carRepository.save(car)
	}


	fun reorderCars(){
		listParkinglots.sortedBy { it.idPlate }
	}
	
	fun getCountOccupiedParkingLots():Int{
		return listParkinglots.size
	}

	fun levelIsFull() : Boolean {
		if(getCountOccupiedParkingLots() < 19) {return false}
		return true
	}
	
	fun carIsInLevel(car: Car) : Boolean{
		for (tmpCar in listParkinglots) {
			if (car.id == tmpCar.id) return true
		}
		return false

	}
}


