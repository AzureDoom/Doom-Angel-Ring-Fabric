package mod.azure.doomangelring;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {
	public static class Server {

		public final ConfigValue<Integer> max_ring_durability;
		public final ConfigValue<Integer> ticks_until_damage;
		public final ConfigValue<Integer> ring_damage_on_tick;
		public final ConfigValue<Boolean> keep_ring_on_death;
		
		public Server(ForgeConfigSpec.Builder builder) {
			max_ring_durability = builder.translation("config.doomangelring.option.max_ring_durability").defineInRange("Max Ring Durability", 900, 1, Integer.MAX_VALUE);
			ticks_until_damage = builder.translation("config.doomangelring.option.ticks_until_damage").defineInRange("Ticks Until Ring Damage", 20, 1, Integer.MAX_VALUE);
			ring_damage_on_tick = builder.translation("config.doomangelring.option.ring_damage_on_tick").defineInRange("Ring Damage on Tick", 1, 1, Integer.MAX_VALUE);
			keep_ring_on_death = builder.translation("config.doomangelring.option.keep_ring_on_death").define("Keep Ring on Death", false);
		}
	}

	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	static {
		final Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER = commonSpecPair.getLeft();
		SERVER_SPEC = commonSpecPair.getRight();
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}
}
