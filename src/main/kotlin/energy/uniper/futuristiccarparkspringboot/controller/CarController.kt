package energy.uniper.futuristiccarparkspringboot.controller

import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import energy.uniper.futuristiccarparkspringboot.service.CarParkService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/car")
class CarController(
		val carRepository: CarRepository,
		val carParkService: CarParkService){
	
	@PostMapping("/")
	fun createNewCar(@RequestParam id: Long): Optional<Car> {
		println("Id $id")
		return carRepository.findById(id)
	}
	
	@GetMapping("/")
	fun getAllCars(): MutableIterable<Car> {
		return carRepository.findAll()
	}
	
	@GetMapping("/parkAll")
	fun parkAllCars(){
		carParkService.parkCar()
	}

	@GetMapping("/{id}")
	fun getCarByID(@PathVariable id: Long): Optional<Car> {
		val car = carRepository.findById(id)
		return car
	}

	@PutMapping("removeCar/{id}")
	fun payParkingLot(@PathVariable id: Long): Pair <Car, Double> {
		val car = carRepository.findById(id).get()
		val price = carParkService.payParkingLot(car)
		return Pair(car, price)
	}


	@DeleteMapping("/{id}")
	fun removeCarFromCarPark(@PathVariable id: Long){
		val car = carRepository.findById(id)
		if(car.isPresent){
			println("Car ${car.get().id} left carpark.")
			return carRepository.delete(car.get())
		}
	}
}