package energy.uniper.futuristiccarparkspringboot.service

import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import org.springframework.stereotype.Component

@Component
class CarParkService(val carRepository: CarRepository) {
	fun parkCar(){
		val carList = carRepository.findAll()
		
		carRepository.saveAll(carList)
	}
	
	fun removeCar(){
	
	}
}