package energy.uniper.futuristiccarparkspringboot.unit

import energy.uniper.futuristiccarparkspringboot.model.Car
import energy.uniper.futuristiccarparkspringboot.pojo.Level
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import energy.uniper.futuristiccarparkspringboot.service.CarParkService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDateTime

class LevelTests {
	companion object{
		val sampleCar = Car()
		val sampleCar2 = Car()
		@BeforeAll @JvmStatic fun initACars(){
			sampleCar.id = 1
			sampleCar.value = 100_001.0
			sampleCar.idPlate = "TEST 123"
			sampleCar.isPartyMember = false
			sampleCar.toControll = false
			sampleCar.enteredAt = LocalDateTime.now()
			
			sampleCar2.isPartyMember = true
		}
	}
	
	@Test
	fun testCalculatePriceForLevel(){
		val level = Level(0)
		val calculatePriceForLevel = level.javaClass.getDeclaredMethod("calculatePriceForLevel", Car::class.java)
		calculatePriceForLevel.isAccessible = true
		
		val result = calculatePriceForLevel.invoke(level, sampleCar)
		
		assertEquals(0.0, result)
	}
}