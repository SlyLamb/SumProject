package com.slylamb.pocketcuisine.Util;

public class Constants {


    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "pocketcuisineDB";
    public static final String TABLE_FAVORITE_RECIPE = "favoriteRecipeTB";
    public static final String TABLE_PLANNED_MEAL = "plannedMealTB";
    public static final String TABLE_SHOPPINGLIST_NAME = "shoppingListTB";
    public static final String TABLE_INGREDIENT = "ingredientTB";


    //Ingredient Shopping List Table columns
    public static final String KEY_STRING_ITEM_ID = "id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_ITEM_STRING = "item_string";
    public static final String KEY_QUANTITY = "quantity_number";
    public static final String KEY_DATE_Time = "date_added";

    // favoriteRecipeTB columns
    public static final String KEY_RECIPE_ID = "id";
    public static final String KEY_RECIPE_TITLE = "recipe_title";
    public static final String KEY_RECIPE_IMAGE = "recipe_image_link";
    public static final String KEY_RECIPE_PUBLISHER = "recipe_publisher";
    public static final String KEY_RECIPE_SOURCE = "recipe_source_url";

    // plannedMealTB columns
    public static final String KEY_PLANNEDMEAL_ID = "planned_meal_id";
    public static final String KEY_PLANNEDMEAL_TITLE = "planned_meal_title";
    public static final String KEY_PLANNEDMEAL_IMAGE = "planned_meal_image_link";
    public static final String KEY_PLANNEDMEAL_PUBLISHER = "planned_meal_publisher";
    public static final String KEY_PLANNEDMEAL_SOURCE = "planned_meal_source_url";
    public static final String KEY_PLANNEDMEAL_DATE = "planned_meal_date";

    // ingredientsTB
    public static final String KEY_INGREDIENT_ID = "ingredient_id";
    public static final String KEY_INGREDIENT_TEXT = "ingredient_text";
    public static final String KEY_INGREDIENT_RECIPE = "ingredient_recipe";
    public static final String KEY_INGREDIENT_PLANNEDMEAL = "ingredient_plannedmeal";

}
