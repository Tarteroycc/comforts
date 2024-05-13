package com.illusivesoulworks.comforts.data;

import com.illusivesoulworks.comforts.ComfortsConstants;
import com.illusivesoulworks.comforts.common.ComfortsRegistry;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

public class ComfortsRecipeProvider extends RecipeProvider {

  public ComfortsRecipeProvider(PackOutput pOutput,
                                CompletableFuture<HolderLookup.Provider> pRegistries) {
    super(pOutput, pRegistries);
  }

  @Override
  protected void buildRecipes(@Nonnull RecipeOutput pRecipeOutput) {
    List<TagKey<Item>> dyes = List.of(
        Tags.Items.DYES_WHITE,
        Tags.Items.DYES_ORANGE,
        Tags.Items.DYES_MAGENTA,
        Tags.Items.DYES_LIGHT_BLUE,
        Tags.Items.DYES_YELLOW,
        Tags.Items.DYES_LIME,
        Tags.Items.DYES_PINK,
        Tags.Items.DYES_GRAY,
        Tags.Items.DYES_LIGHT_GRAY,
        Tags.Items.DYES_CYAN,
        Tags.Items.DYES_PURPLE,
        Tags.Items.DYES_BLUE,
        Tags.Items.DYES_BROWN,
        Tags.Items.DYES_GREEN,
        Tags.Items.DYES_RED,
        Tags.Items.DYES_BLACK
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
    colorWithDye(pRecipeOutput, dyes, hammocks, "comforts:hammock");
    colorWithDye(pRecipeOutput, dyes, sleepingBags, "comforts:sleeping_bag");
    Item ropeAndNail = ComfortsRegistry.ROPE_AND_NAIL_ITEM.get();

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ropeAndNail, 2)
        .define('A', Tags.Items.STRINGS)
        .define('X', Tags.Items.INGOTS_IRON)
        .pattern("AA ")
        .pattern("AX ")
        .pattern("  A")
        .group("comforts:rope_and_nail")
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .save(pRecipeOutput);

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ropeAndNail, 2)
        .requires(Tags.Items.INGOTS_IRON)
        .requires(Tags.Items.ROPES)
        .group("comforts:rope_and_nail")
        .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
        .save(pRecipeOutput, ComfortsConstants.MOD_ID + ":shapeless_" + getItemName(ropeAndNail));
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

  protected static void sleepingBag(RecipeOutput pRecipeOutput, ItemLike pBed,
                                    ItemLike pWool) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pBed)
        .define('#', pWool)
        .pattern("###")
        .group("comforts:sleeping_bag")
        .unlockedBy(getHasName(pWool), has(pWool))
        .save(pRecipeOutput);
  }

  protected static void hammock(RecipeOutput pRecipeOutput, ItemLike pBed,
                                ItemLike pWool) {
    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pBed)
        .define('#', pWool)
        .define('S', Tags.Items.STRINGS)
        .define('X', Tags.Items.RODS_WOODEN)
        .pattern("SXS")
        .pattern("S#S")
        .pattern("SXS")
        .group("comforts:hammock")
        .unlockedBy(getHasName(pWool), has(pWool))
        .save(pRecipeOutput);
  }
}
