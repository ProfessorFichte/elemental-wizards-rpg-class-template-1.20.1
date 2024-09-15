package net.elemental_wizards_rpg.item.weapons;

import net.elemental_wizards_rpg.ElementalMod;
import net.elemental_wizards_rpg.item.ElementalGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.weapon.StaffItem;
import net.spell_engine.api.item.weapon.Weapon;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

public class WeaponsRegister {
    public static final ArrayList<Weapon.Entry> entries = new ArrayList<>();

    private static Weapon.Entry entry(String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        return entry(null, name, material, item, defaults);
    }

    private static Weapon.Entry entry(String requiredMod, String name, Weapon.CustomMaterial material, Item item, ItemConfig.Weapon defaults) {
        var entry = new Weapon.Entry(ElementalMod.MOD_ID, name, material, item, defaults, null);
        if (entry.isRequiredModInstalled()) {
            entries.add(entry);
        }
        return entry;
    }

    private static Supplier<Ingredient> ingredient(String idString) {
        return ingredient(idString, Items.DIAMOND);
    }

    private static Supplier<Ingredient> ingredient(String idString, Item fallback) {
        var id = new Identifier(idString);
        return () -> {
            var item = Registries.ITEM.get(id);
            var ingredient = item != null ? item : fallback;
            return Ingredient.ofItems(ingredient);
        };
    }

    //WANDS
    private static final float wandAttackDamage = 2;
    private static final float wandAttackSpeed = -2.4F;
    private static Weapon.Entry wand(String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        var item = new StaffItem(material, settings);
        return entry(name, material, item, new ItemConfig.Weapon(wandAttackDamage, wandAttackSpeed));
    }
    public static final Weapon.Entry kelpWand = wand("wand_kelp",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.ofItems(Items.STICK)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 1));
    public static final Weapon.Entry aquaWand = wand("wand_aqua",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 2));
    public static final Weapon.Entry netheriteAquaWand = wand("wand_netherite_aqua",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 3));

    public static final Weapon.Entry clayWand = wand("wand_clay",
            Weapon.CustomMaterial.matching(ToolMaterials.WOOD, () -> Ingredient.ofItems(Items.STICK)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 1));
    public static final Weapon.Entry terraWand = wand("wand_terra",
            Weapon.CustomMaterial.matching(ToolMaterials.IRON, () -> Ingredient.ofItems(Items.IRON_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 2));
    public static final Weapon.Entry netheriteTerraWand = wand("wand_netherite_terra",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 3));

    //STAFFS
    private static final float staffAttackDamage = 4;
    private static final float staffAttackSpeed = -3F;
    private static Weapon.Entry staff(String name, Weapon.CustomMaterial material) {
        return staff(null, name, material);
    }
    private static Weapon.Entry staff(String requiredMod, String name, Weapon.CustomMaterial material) {
        var settings = new Item.Settings();
        var item = new StaffItem(material, settings);
        return entry(requiredMod, name, material, item, new ItemConfig.Weapon(staffAttackDamage, staffAttackSpeed));
    }
    public static final Weapon.Entry aquaStaff= staff("staff_aqua",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 4));
    public static final Weapon.Entry netheriteAquaStaff = staff("staff_netherite_aqua",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 5))
            //.attribute(ItemConfig.Attribute.multiply(Objects.requireNonNull(Identifier.tryParse("more_rpg_classes:rage_modifier")),1))
            ;
    public static final Weapon.Entry terraStaff= staff("staff_terra",
            Weapon.CustomMaterial.matching(ToolMaterials.DIAMOND, () -> Ingredient.ofItems(Items.GOLD_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 4));
    public static final Weapon.Entry netheriteTerraStaff = staff("staff_netherite_terra",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 5))
            ;

    public static final Weapon.Entry rubyTerraStaff = staff("betternether","staff_ruby_terra",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.EARTH.id, 6))
            ;
    public static final Weapon.Entry crystalAquaStaff = staff("betterend","staff_crystal_aqua",
            Weapon.CustomMaterial.matching(ToolMaterials.NETHERITE, ingredient("betterend:aeternium_ingot")))
            .attribute(ItemConfig.Attribute.bonus(MoreSpellSchools.WATER.id, 6))
            ;

    //Registration
    public static void register(Map<String,ItemConfig.Weapon> configs) {
        Weapon.register(configs, entries, ElementalGroup.ELEMENTAL_WIZARD_KEY);
    }
}