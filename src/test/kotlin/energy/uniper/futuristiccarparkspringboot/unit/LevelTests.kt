package energy.uniper.futuristiccarparkspringboot.unit

import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import energy.uniper.futuristiccarparkspringboot.service.CarParkService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class LevelTests {
	companion object{
		val sampleCar = Car()
		@BeforeAll @JvmStatic fun initACar(){
			sampleCar.id = 1
			sampleCar.value = 100_001.0
			sampleCar.idPlate = "TEST 123"
			sampleCar.isPartyMember = false
			sampleCar.toControll = false
		}
	}
	
	@Test
	fun testCarIsInList(){
		val levelClass = Level(0)
		
		levelClass.parkCar(sampleCar)
		
		assertEquals(true, levelClass.carIsInLevel(sampleCar))
	}
	
	@Test
	fun testSetControll(){
		val carRepository = mock(CarRepository::class.java)
		val carParkService = CarParkService(carRepository)
		
		assertEquals(1, carParkService.getLevel(sampleCar))
	}
	
}