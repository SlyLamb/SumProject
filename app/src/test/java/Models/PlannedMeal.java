package Models;

import android.provider.CalendarContract;
import java.util.Date;

public class PlannedMeal {
    private Recipe recipe;
    private Date date;      // Date that the recipe is scheduled for
    private CalendarContract.Reminders reminder; // Reminder for meal (might use different type)
    private char mealType;  // Type of meal: b=breakfast, l=lunch, d=dinner and s=snack
}
