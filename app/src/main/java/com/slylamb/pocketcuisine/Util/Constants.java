package com.slylamb.pocketcuisine.Util;

public class Constants {


    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "pocketcuisineDB";
    public static final String TABLE_PLANNED_MEAL = "plannedMealTB";
    public static final String TABLE_SHOPPINGLIST_NAME = "shoppingListTB";
    public static final String TABLE_FAVOURITE_RECIPE = "favouriteRecipeTB";

    //Table columns
    public static final String KEY_STRING_ITEM_ID = "id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_ITEM_STRING = "item_string";
    public static final String KEY_QUANTITY = "quantity_number";
    public static final String KEY_DATE_Time = "date_added";

    //Planned Meal Table columns
    public static final String KEY_PLANNEDMEAL_URL = "url";
    public static final String KEY_PLANNEDMEAL_IMAGELINK="image_link";
    public static final String KEY_PLANNEDMEAL_NAME = "name";
    public static final String KEY_PLANNEDMEAL_ID = "planned_meal_id";


    // favoriteRecipeTB columns
    public static final String KEY_RECIPE_ID = "id";
    public static final String KEY_RECIPE_TITLE = "recipe_title";
    public static final String KEY_RECIPE_IMAGE = "recipe_image_link";
    public static final String KEY_RECIPE_PUBLISHER = "recipe_publisher";
    public static final String KEY_RECIPE_SOURCE = "recipe_source_url";
    public static final String KEY_RECIPE_RECIPEID = "recipe_id";

}
