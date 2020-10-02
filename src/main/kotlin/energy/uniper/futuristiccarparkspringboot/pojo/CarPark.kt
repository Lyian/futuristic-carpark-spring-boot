package energy.uniper.futuristiccarparkspringboot.pojo


class CarPark {
    val levels = mutableListOf<Level>()

    init {
        for (i in 0..10) {
            levels.add(Level(i))
        }
    }



}