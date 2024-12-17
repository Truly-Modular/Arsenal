package smartin.arsenal;

import dev.architectury.event.events.common.LifecycleEvent;

public class Arsenal
{
	public static final String MOD_ID = "tm_arsenal";

	public static void init() {
		LifecycleEvent.SETUP.register(GenerateModularConverters::new);
	}
}