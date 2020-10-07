package energy.uniper.futuristiccarparkspringboot.unit

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import energy.uniper.futuristiccarparkspringboot.service.CarParkService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class CarServiceTests {
	companion object{
		private val sampleCar = Car()
		private val sampleCar2 = Car()
		val carRepository: CarRepository = mock(CarRepository::class.java)
		val carParkService = CarParkService(carRepository)
		@BeforeAll @JvmStatic fun initACars(){
			sampleCar.id = 1
			sampleCar.value = 100_001.0
			sampleCar.idPlate = "TEST 123"
			sampleCar.isPartyMember = false
			sampleCar.toControll = false
			sampleCar.status = CarStatus.REQUESTING
			
			sampleCar2.isPartyMember = true
		}
	}
	
	@Test
	fun testParkingACarStatus(){
		Mockito.`when`(carRepository.findAll()).thenReturn(mutableListOf(sampleCar))
		
		val parkedCar = carParkService.parkCar()[0]
		
		assertEquals(CarStatus.PARKED, parkedCar.status)
	}
}


