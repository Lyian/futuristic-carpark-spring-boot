package energy.uniper.futuristiccarparkspringboot.controller

import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/car")
class CarController(val carRepository: CarRepository){
	
	@PostMapping("/")
	fun createNewCar(@RequestParam id: Long): Optional<Car> {
		println("Id $id")
		return carRepository.findById(id)
	}
	
	@GetMapping("/")
	fun getAllCars(): MutableIterable<Car> {
		return carRepository.findAll()
	}
	
	@GetMapping("/{id}")
	fun getCarByID(@PathVariable id: Long): Optional<Car> {
		return carRepository.findById(id)
	}
}