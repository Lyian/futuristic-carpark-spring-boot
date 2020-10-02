package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sqrt

class Level (val levelId: Int){
	// Die Position in der Liste gibt die id des Parkplatzes an.
	val listParkinglots = mutableListOf<Car>()

	val numberOfParkingLots = 20

	fun calculatePriceForLevel(car: Car) : Double {

		val durationInHours = Duration.between(car.enteredAt, car.leftAt).toHours()

		return durationInHours * sqrt(this.levelId.toDouble())
	}
	
	fun addCar(car: Car){
		if(!isLevelFull()){
			listParkinglots.add(car)
			car.status = CarStatus.PARKED
			car.level = this.levelId
			car.enteredAt = LocalDateTime.now()
			reorderCars()
		} else {
			//TODO Was mache ich, wenn das Level hier voll ist? Exception?
		}
	}
	
	// nachdem ein Auto hinzugefügt wurde, soll die Liste wieder nach kennzeichen sortiert sein,
	// da aber gerade die Position in der Liste die Reihenfolge der Parkplätze angibt, müssen wir lediglich
	// die Liste sortieren.
	fun reorderCars(){
		listParkinglots.sortBy { it.idPlate }
	}
	
	fun getCountOccupiedParkingLots():Int{
		return listParkinglots.size
	}
	
	fun carIsInLevel(car: Car) : Boolean{
		return listParkinglots.contains(car)
	}

	fun isLevelFull() : Boolean {
		return getCountOccupiedParkingLots() >= numberOfParkingLots
	}
}