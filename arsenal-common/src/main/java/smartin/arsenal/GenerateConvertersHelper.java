package smartin.arsenal;

import net.minecraft.world.item.*;
import smartin.miapi.Miapi;
import smartin.miapi.item.ModularItemStackConverter;
import smartin.miapi.material.MaterialProperty;
import smartin.miapi.material.base.Material;
import smartin.miapi.modules.MutableModuleInstance;
import smartin.miapi.modules.properties.ItemIdProperty;
import smartin.miapi.registries.RegistryInventory;

import java.util.List;

public class GenerateConvertersHelper {

    public static void setupTools(List<TieredItem> toolItems, Material material) {
        // Axe
        toolItems.stream()
                .filter(AxeItem.class::isInstance)
                .findFirst()
                .ifPresent(item -> {
                    ModularItemStackConverter.converters.add(stack -> {
                        if (stack.getItem().equals(item)) {
                            return axeItem(material);
                        }
                        return stack;
                    });
                });

        // Shovel
        toolItems.stream()
                .filter(ShovelItem.class::isInstance)
                .findFirst()
                .ifPresent(item -> {
                    ModularItemStackConverter.converters.add(stack -> {
                        if (stack.getItem().equals(item)) {
                            return shovelItem(material);
                        }
                        return stack;
                    });
                });

        // Pickaxe
        toolItems.stream()
                .filter(PickaxeItem.class::isInstance)
                .findFirst()
                .ifPresent(item -> {
                    ModularItemStackConverter.converters.add(stack -> {
                        if (stack.getItem().equals(item)) {
                            return pickaxeItem(material);
                        }
                        return stack;
                    });
                });

        // Hoe
        toolItems.stream()
                .filter(HoeItem.class::isInstance)
                .findFirst()
                .ifPresent(item -> {
                    ModularItemStackConverter.converters.add(stack -> {
                        if (stack.getItem().equals(item)) {
                            return hoeItem(material);
                        }
                        return stack;
                    });
                });

        // Sword
        toolItems.stream()
                .filter(SwordItem.class::isInstance)
                .findFirst()
                .ifPresent(item -> {
                    ModularItemStackConverter.converters.add(stack -> {
                        if (stack.getItem().equals(item)) {
                            return swordItem(material);
                        }
                        return stack;
                    });
                });
    }

    public static ItemStack swordItem(Material material) {
        MutableModuleInstance handleModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "handle/sword"),Miapi.registryAccess);
        MaterialProperty.setMaterial(handleModule, getWoodMaterial()); // Set material to input material
        // Define the child 'guard' module for the handle
        MutableModuleInstance guardModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "guard/normal"),Miapi.registryAccess);
        MaterialProperty.setMaterial(guardModule, material);
        // Define the child 'blade' module for the guard
        MutableModuleInstance bladeModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "blade/normal"),Miapi.registryAccess);
        MaterialProperty.setMaterial(bladeModule, material);
        // Set blade as child of guard
        guardModule.setChild("blade", bladeModule);
        // Define the 'pommel' module for the handle
        MutableModuleInstance pommelModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "pommel/round"),Miapi.registryAccess);
        MaterialProperty.setMaterial(pommelModule, material); // Set pommel material to gold
        // Set guard and pommel as children of handle
        handleModule.setChild("guard", guardModule);
        handleModule.setChild("pommel", pommelModule);
        // Create the ItemStack for the sword with the handle module configured
        ItemStack swordItem = new ItemStack(RegistryInventory.modularItem); // Use the material specified for the sword base
        handleModule.toRecord().writeToItem(swordItem);
        swordItem = ItemIdProperty.changeId(swordItem);
        return swordItem;
    }

    public static ItemStack shovelItem(Material material) {
        // Handle module for the shovel
        MutableModuleInstance handleModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "handle/tool"),Miapi.registryAccess);
        MaterialProperty.setMaterial(handleModule, getWoodMaterial());

        // Guard module
        MutableModuleInstance guardModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "guard/tool_adapter"),Miapi.registryAccess);

        // Tool head module for shovel
        MutableModuleInstance toolHeadModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/shovel"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolHeadModule, material);

        // Set hierarchy
        guardModule.setChild("tool_head", toolHeadModule);
        handleModule.setChild("guard", guardModule);

        // Create ItemStack
        ItemStack shovelItem = new ItemStack(RegistryInventory.modularItem);
        handleModule.toRecord().writeToItem(shovelItem);
        shovelItem = ItemIdProperty.changeId(shovelItem);

        return shovelItem;
    }

    public static ItemStack axeItem(Material material) {
        MutableModuleInstance handleModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "handle/tool"),Miapi.registryAccess);
        MaterialProperty.setMaterial(handleModule, getWoodMaterial());

        MutableModuleInstance guardModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "guard/tool_adapter"),Miapi.registryAccess);

        MutableModuleInstance toolHeadModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/axe_front"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolHeadModule, material);

        MutableModuleInstance toolBackModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/tool_back"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolBackModule, material);

        toolHeadModule.setChild("tool_back", toolBackModule);
        guardModule.setChild("tool_head", toolHeadModule);
        handleModule.setChild("guard", guardModule);

        ItemStack axeItem = new ItemStack(RegistryInventory.modularItem);
        handleModule.toRecord().writeToItem(axeItem);
        axeItem = ItemIdProperty.changeId(axeItem);

        return axeItem;
    }

    public static ItemStack pickaxeItem(Material material) {
        MutableModuleInstance handleModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "handle/tool"),Miapi.registryAccess);
        MaterialProperty.setMaterial(handleModule, getWoodMaterial());

        MutableModuleInstance guardModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "guard/tool_adapter"), Miapi.registryAccess);

        MutableModuleInstance toolHeadModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/pickaxe_front"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolHeadModule, material);

        MutableModuleInstance toolBackModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/pickaxe_back"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolBackModule, material);

        toolHeadModule.setChild("tool_back", toolBackModule);
        guardModule.setChild("tool_head", toolHeadModule);
        handleModule.setChild("guard", guardModule);

        ItemStack pickaxeItem = new ItemStack(RegistryInventory.modularItem);
        handleModule.toRecord().writeToItem(pickaxeItem);
        pickaxeItem = ItemIdProperty.changeId(pickaxeItem);

        return pickaxeItem;
    }

    public static ItemStack hoeItem(Material material) {
        MutableModuleInstance handleModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "handle/tool"),Miapi.registryAccess);
        MaterialProperty.setMaterial(handleModule, getWoodMaterial());

        MutableModuleInstance guardModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "guard/tool_adapter"),Miapi.registryAccess);

        MutableModuleInstance toolHeadModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/hoe_front"), Miapi.registryAccess);
        MaterialProperty.setMaterial(toolHeadModule, material);

        MutableModuleInstance toolBackModule = new MutableModuleInstance(Miapi.id("tm_arsenal", "tool/tool_back"),Miapi.registryAccess);
        MaterialProperty.setMaterial(toolBackModule, material);

        toolHeadModule.setChild("tool_back", toolBackModule);
        guardModule.setChild("tool_head", toolHeadModule);
        handleModule.setChild("guard", guardModule);

        ItemStack hoeItem = new ItemStack(RegistryInventory.modularItem);
        handleModule.toRecord().writeToItem(hoeItem);
        hoeItem = ItemIdProperty.changeId(hoeItem);

        return hoeItem;
    }

    public static Material getWoodMaterial() {
        return RegistryInventory.MATERIAL_REGISTRY.get(Miapi.id("wood/wood"));
    }


}
