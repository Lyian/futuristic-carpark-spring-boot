package energy.uniper.futuristiccarparkspringboot.pojo


class CarPark {
    val levels = mutableListOf<Level>()

    init {
        // i ist nicht aussagekräftig
        for (level in 0..10) {
            levels.add(Level(level))
        }
    }



}