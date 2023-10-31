package mod.azure.doomangelring;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;

@Config(id = DoomAngelRing.MODID)
public class DoomAngelRingConfig {

	@Configurable
	@Configurable.Synchronized
	@Configurable.Range(min = 1)
	public int max_ring_durability = 900;
	@Configurable
	@Configurable.Synchronized
	@Configurable.Range(min = 1)
	public int ticks_until_damage = 20;
	@Configurable
	@Configurable.Synchronized
	@Configurable.Range(min = 1)
	public int ring_damage_on_tick = 1;
}
