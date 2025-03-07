package smartin.arsenal;

import dev.architectury.event.EventResult;
import smartin.miapi.events.MiapiEvents;

public class Arsenal {
    public static final String MOD_ID = "tm_arsenal";

    public static void init() {
        MiapiEvents.GENERATE_MATERIAL_CONVERTERS.register((material, tools, armorItems, isClient) -> {
            GenerateConvertersHelper.setupTools(tools, material);
            return EventResult.pass();
        });
    }
}