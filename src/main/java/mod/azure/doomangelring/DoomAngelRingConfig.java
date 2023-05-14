package mod.azure.doomangelring;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;

@Config(id = DoomAngelRing.MODID)
public class DoomAngelRingConfig {

	@Configurable
	public int max_ring_durability = 900;
	@Configurable
	public int ticks_until_damage = 20;
	@Configurable
	public int ring_damage_on_tick = 1;
	@Configurable
	public boolean keep_ring_on_death = false;
}
