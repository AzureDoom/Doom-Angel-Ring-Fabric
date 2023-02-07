package mod.azure.doomangelring;

import eu.midnightdust.lib.config.MidnightConfig;

public class DoomAngelRingConfig extends MidnightConfig {

	@Entry public static int max_ring_durability = 900;
	@Entry public static int ticks_until_damage = 20;
	@Entry public static int ring_damage_on_tick = 1;
	@Entry public static boolean keep_ring_on_death = false;
}
