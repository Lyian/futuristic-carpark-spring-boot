package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.hibernate.criterion.Projections.count
import java.time.LocalDateTime
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.random.Random

class Level (val levelId: Int){
	// Die Position in der Liste gibt die id des Parkplatzes an.
	val listParkinglots = mutableListOf<Car>()

	fun calculatePriceForLevel(car: Car) : Double {
		
		return 0.0
	}
	
	fun addCar(car: Car){
		listParkinglots.add(car)
	}
	
	// nachdem ein Auto hinzugefügt wurde, soll die Liste wieder nach kennzeichen sortiert sein,
	// da aber gerade die Position in der Liste die Reihenfolge der Parkplätze angibt, müssen wir lediglich
	// die Liste sortieren.

	fun parkingLotFree(): Boolean{
		if (listParkinglots.size < 19){
			return true
		}
		return false
	}

	fun parkCar(car: Car) {
		addCar(car)
		car.enteredAt = LocalDateTime.now()
		car.level = levelId
		sortAlphabetic()
		setControl(car)
	}

	fun sortAlphabetic() {
		listParkinglots.sortedBy{it.idPlate}
	}

	fun setControl(car: Car) {
		var potKontrolle = listParkinglots.filter { it.idPlate == car.idPlate && it.level!! > 4  }
		if (potKontrolle.count() > 0) {
			listParkinglots?.find { it.idPlate == car.idPlate }?.toControll = 0.01 * potKontrolle[0].level!! > Random.nextDouble(0.0, 1.0)
		}
	}


	fun carIsInLevel(car: Car) : Boolean{
		for (parkingLot in listParkinglots){
			if (parkingLot.idPlate == car.idPlate) {return true}
		}
		return false
	}
	fun removeCar(car: Car): Double{
		val abholung = LocalDateTime.now().plusHours(Random.nextLong(0, 10))
		val price = round((abholung.hour - car.enteredAt!!.hour).toDouble() * sqrt(car.level!!.toDouble()) *100) /100
		println(" ${car.idPlate} ${price}")
		listParkinglots.remove(car)
		return price
	}



}