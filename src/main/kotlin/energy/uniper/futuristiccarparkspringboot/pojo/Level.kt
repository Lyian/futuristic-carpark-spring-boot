package energy.uniper.futuristiccarparkspringboot.pojo

import energy.uniper.futuristiccarparkspringboot.model.Car

class Level (val levelId: Int){
	// Die Position in der Liste gibt die id des Parkplatzes an.
	val listParkinglots = mutableListOf<Car>()
	
	fun calculatePriceForLevel(car: Car) : Double {
		
		return 0.0
	}
	
	fun addCar(car: Car){

	}
	
	// nachdem ein Auto hinzugefügt wurde, soll die Liste wieder nach kennzeichen sortiert sein,
	// da aber gerade die Position in der Liste die Reihenfolge der Parkplätze angibt, müssen wir lediglich
	// die Liste sortieren.
	fun reorderCars(){
	
	}
	
	fun getCountOccupiedParkingLots():Int{
		return 0
	}
	
	fun carIsInLevel(car: Car) : Boolean{
		return false
	}
}