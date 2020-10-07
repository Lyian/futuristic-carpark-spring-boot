package energy.uniper.futuristiccarparkspringboot.integration

import energy.uniper.futuristiccarparkspringboot.enum.CarStatus
import energy.uniper.futuristiccarparkspringboot.repository.CarRepository
import energy.uniper.futuristiccarparkspringboot.unit.CarServiceTests.Companion.carRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {
	
	@Autowired
	private val mvc: MockMvc? = null
	
	@Autowired
	private val carRepository: CarRepository? = null
	
	@Test
	fun testParkedCarsExists(){
		mvc!!.perform(get("http://localhost:8080/api/car/parkAll")).andExpect(status().isOk).andExpect(jsonPath("$").exists())
	}
	
	@Test // diskussion in memory database vs dockerized real database
	fun testNoSlotsCarsExists(){
		mvc!!.perform(get("http://localhost:8080/api/car/parkAll")).andExpect(status().isOk)
		val allCars = carRepository!!.findAll().filter { it.status == CarStatus.NOSLOTS }
		
		Assertions.assertFalse(allCars::isEmpty)
	}

}