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
		val amountOfLevels = carPark.levels.size

		for(car in carList){
			val levelOfCurrentCar = determineParkedLevel(car)
			if(levelOfCurrentCar < amountOfLevels){
				carPark.levels[levelOfCurrentCar].addCar(car)
				markCarForControl(car)
			}
			// else in die nächste Zeile
			else {
				car.status = CarStatus.NOSLOTS
				//TODO Auto kann nicht geparkt werden
				println("Voll!!")
			}
		}
		carRepository.saveAll(carList)
	}

	// Die Funktion tut 2 Dinge - initial und determinedLevel bestimmen -> in 2 Funktionen auslagern
	fun determineInitialLevel(car: Car) : Int{
		// level ist ein objekt - var umbenennen
		var initialLevel: Int = 99

		if (car.value!! > 100000 || car.isPartyMember!!){
			initialLevel = 0
		} else{
			initialLevel = ((10 - kotlin.math.floor(car.value / 10000)).toInt())
		}
		// parkedLevel in neue Funktion verschoben
		// markCarForControl gehört nicht zur Bestimmung eines Leves - Verschieben zur Park-Prozedur

		return initialLevel
	}

	fun determineParkedLevel(car : Car) : Int{
		val initialLevel = determineInitialLevel(car)
		var parkedLevel = initialLevel

		while (carPark.levels[parkedLevel].isLevelFull()) {
			if(parkedLevel < carPark.levels.size){
				parkedLevel += 1
			}
			else {
				return 99
			}
		}
		return parkedLevel
	}


	// 3 Parameter
	// level nicht benötigt, da beim parken das level im car objekt abgespeichert wird
	// initialLevel nicht benötigt, da über determineLevel aufrufbar
	fun markCarForControl(car: Car){
		val initialLevel = determineInitialLevel(car)
		val eps = Random.nextDouble(0.0,1.0)

		if(car.level == 0 && !car.isPartyMember!! && eps <= 0.001){
			car.toControll = true
		} else car.toControll = initialLevel > 4 && eps <= 0.01 * car.level!!

	}
	
	fun removeCar(car: Car){

		carPark.levels[car.level!!].listParkinglots.remove(car)
		car.leftAt = car.enteredAt?.plusHours(Random.nextLong(1,24))
		val priceForCar = carPark.levels[car.level!!].calculatePriceForLevel(car)
		println(car.idPlate + " muss " + priceForCar + "€ bezahlen.")
		car.status = CarStatus.LEFT
	}
}

























