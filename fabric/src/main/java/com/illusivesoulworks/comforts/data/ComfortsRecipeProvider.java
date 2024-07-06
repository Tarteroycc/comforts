package com.illusivesoulworks.comforts.data;

import com.illusivesoulworks.comforts.ComfortsConstants;
import com.illusivesoulworks.comforts.common.ComfortsRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AndResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.TagsPopulatedResourceCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ComfortsRecipeProvider extends FabricRecipeProvider {

  public ComfortsRecipeProvider(FabricDataOutput output,
                                CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void buildRecipes(@Nonnull RecipeOutput pRecipeOutput) {
    List<TagKey<Item>> dyes = List.of(
        ConventionalItemTags.WHITE_DYES,
        ConventionalItemTags.ORANGE_DYES,
        ConventionalItemTags.MAGENTA_DYES,
        ConventionalItemTags.LIGHT_BLUE_DYES,
        ConventionalItemTags.YELLOW_DYES,
        ConventionalItemTags.LIME_DYES,
        ConventionalItemTags.PINK_DYES,
        ConventionalItemTags.GRAY_DYES,
        ConventionalItemTags.LIGHT_GRAY_DYES,
        ConventionalItemTags.CYAN_DYES,
        ConventionalItemTags.PURPLE_DYES,
        ConventionalItemTags.BLUE_DYES,
        ConventionalItemTags.BROWN_DYES,
        ConventionalItemTags.GREEN_DYES,
        ConventionalItemTags.RED_DYES,
        ConventionalItemTags.BLACK_DYES
    );
    List<Item> hammocks = ComfortsRegistry.HAMMOCKS.values().stream()
        .map(blockRegistryObject -> blockRegistryObject.get().asItem()).toList();
    List<Item> sleepingBags = ComfortsRegistry.SLEEPING_BAGS.values().stream()
        .map(blockRegistryObject -> blockRegistryObject.get().asItem()).toList();

    List<Item> wool = List.of(
        Items.WHITE_WOOL,
        Items.ORANGE_WOOL,
        Items.MAGENTA_WOOL,
        Items.LIGHT_BLUE_WOOL,
        Items.YELLOW_WOOL,
        Items.LIME_WOOL,
        Items.PINK_WOOL,
        Items.GRAY_WOOL,
        Items.LIGHT_GRAY_WOOL,
        Items.CYAN_WOOL,
        Items.PURPLE_WOOL,
        Items.BLUE_WOOL,
        Items.BROWN_WOOL,
        Items.GREEN_WOOL,
        Items.RED_WOOL,
        Items.BLACK_WOOL
    );

    for (int i = 0; i < wool.size(); i++) {
      sleepingBag(pRecipeOutput, sleepingBags.get(i), wool.get(i));
      hammock(pRecipeOutput, hammocks.get(i), wool.get(i));
    }
    colorWithDye(withConditions(pRecipeOutput, HammockEnabledCondition.INSTANCE), dyes, hammocks,
        "comforts:hammock");
    colorWithDye(withConditions(pRecipeOutput, SleepingBagEnabledCondition.INSTANCE), dyes,
        sleepingBags, "comforts:sleeping_bag");
    Item ropeAndNail = ComfortsRegistry.ROPE_AND_NAIL_ITEM.get();

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ropeAndNail, 2)
        .define('A', ConventionalItemTags.STRINGS)
        .define('X', ConventionalItemTags.IRON_INGOTS)
        .pattern("AA ")
        .pattern("AX ")
        .pattern("  A")
        .group("comforts:rope_and_nail")
        .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
        .save(withConditions(pRecipeOutput, HammockEnabledCondition.INSTANCE));


    List<ResourceCondition> conditions = new ArrayList<>();
    conditions.add(HammockEnabledCondition.INSTANCE);
    conditions.add(new TagsPopulatedResourceCondition(ConventionalItemTags.ROPES));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ropeAndNail, 2)
        .requires(ConventionalItemTags.IRON_INGOTS)
        .requires(ConventionalItemTags.ROPES)
        .group("comforts:rope_and_nail")
        .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
        .save(withConditions(pRecipeOutput, new AndResourceCondition(conditions)),
            ComfortsConstants.MOD_ID + ":shapeless_" + getItemName(ropeAndNail));
  }

  protected static void colorWithDye(RecipeOutput pRecipeOutput, List<TagKey<Item>> pDyes,
                                     List<Item> pDyeableItems, String pGroup) {

    for (int i = 0; i < pDyes.size(); i++) {
      TagKey<Item> dye = pDyes.get(i);
      Item item = pDyeableItems.get(i);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, item)
          .requires(dye)
          .requires(Ingredient.of(
              pDyeableItems.stream().filter(p_288265_ -> !p_288265_.equals(item))
                  .map(ItemStack::new)))
          .group(pGroup)
          .unlockedBy("has_needed_dye", has(dye))
          .save(pRecipeOutput, ComfortsConstants.MOD_ID + ":dye_" + getItemName(item));
    }
  }

  protected void sleepingBag(RecipeOutput pRecipeOutput, ItemLike pBed,
                             ItemLike pWool) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pBed)
        .define('#', pWool)
        .pattern(" # ")
        .pattern(" # ")
        .pattern(" # ")
        .group("comforts:sleeping_bag")
        .unlockedBy(getHasName(pWool), has(pWool))
        .save(withConditions(pRecipeOutput, SleepingBagEnabledCondition.INSTANCE));
  }

  protected void hammock(RecipeOutput pRecipeOutput, ItemLike pBed,
                         ItemLike pWool) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pBed)
        .define('#', pWool)
        .define('S', ConventionalItemTags.STRINGS)
        .define('X', ConventionalItemTags.WOODEN_RODS)
        .pattern(" X ")
        .pattern("S#S")
        .pattern(" X ")
        .group("comforts:hammock")
        .unlockedBy(getHasName(pWool), has(pWool))
        .save(withConditions(pRecipeOutput, HammockEnabledCondition.INSTANCE));
  }
}
