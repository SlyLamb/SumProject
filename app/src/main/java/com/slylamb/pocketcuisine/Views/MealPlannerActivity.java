/* com.slylamb.pocketcuisine.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.slylamb.pocketcuisine.Models.MealPlanner;
import com.slylamb.pocketcuisine.Presenters.MealPlannerActivityPresenter;
import com.slylamb.pocketcuisine.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MealPlannerActivity extends AppCompatActivity implements MealPlannerActivityPresenter.View {

    private MealPlannerActivityPresenter presenter;
    private CaldroidFragment calendar;
    private Button btnGenerateList;
    final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_planner);
        // Initialise presented and button
        presenter = new MealPlannerActivityPresenter(this);
        btnGenerateList = findViewById(R.id.btn_generate_list);
        // Initialise caldroid calendar
        calendar = new CaldroidFragment();
        // If activity is created after rotation
        if (savedInstanceState != null) {
            calendar.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
        } else {    // Otherwise, if activity is fresh
            Bundle args = new Bundle();
            // Get calendar instance
            Calendar cal = Calendar.getInstance();
            // Put month, year, and enable swiping to next and previous months and six weeks in calendar to show
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH)+1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            calendar.setArguments(args);
        }
        setDatesWithMeals();

        // Attach to the activity
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.calendar, calendar);
        transaction.commit();

        // Setup calendar listener
        calendar.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                // Get string format of date clicked on
                String dateString = formatter.format(date);
                // Check if there are planned meals on selected date
                if (presenter.hasMealOnDate(dateString)) {
                    // If there are, go to PlannedMealsActivity and pass it the date string
                    Intent intent = new Intent(getApplicationContext(), PlannedMealsActivity.class);
                    intent.putExtra("date", dateString);
                    startActivity(intent);
                } else {
                    // Otherwise, let user know there's nothing on chosen date
                    Toast.makeText(getBaseContext(), "You have no planned meals on " + dateString,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Setup add to shopping list button listener
        btnGenerateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShoppingListDialog();
            }
        });
    }

    // Set dates with meals with different background to those without meals yet
    @Override
    public void setDatesWithMeals() {
        Calendar cal = Calendar.getInstance();
        // Get list of dates with events on them
        ArrayList<String> datesWithMeals = presenter.getDatesWithMeals();
        for (int i = 0; i < datesWithMeals.size(); i++) {
            // Get calendar date from each date with meal
            try {
                cal.setTime(formatter.parse(datesWithMeals.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Set its background to gray to differentiate to unplanned dates
            ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.caldroid_gray));
            calendar.setBackgroundDrawableForDate(background, cal.getTime());
        }
    }

    // Open dialog for user to pick name and date range for shopping list
    @Override
    public void showShoppingListDialog() {
        // Inflate dialog layout
        LayoutInflater myLayout = LayoutInflater.from(MealPlannerActivity.this);
        final View view = myLayout.inflate(R.layout.generate_shopping_list_dialog, null);
        // Dialog has 3 edit texts, one for the name, one for the from date and the other for the to date
        final EditText etxtName = view.findViewById(R.id.etxt_name);
        final EditText etxtFromDate = view.findViewById(R.id.etxt_from_date);
        final EditText etxtToDate = view.findViewById(R.id.etxt_to_date);
        // Create dialog with title, message, and positive and negative behaviours
        new AlertDialog.Builder(MealPlannerActivity.this).setTitle("Add to Shopping Lists:")
                .setMessage("Pick a name and a date range").setCancelable(true).setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Todo: checks, if and elses with Toasts
                        // Add meal to meal planner
                        presenter.newShoppingList(etxtName.getText().toString(), etxtFromDate.getText().toString(), etxtToDate.getText().toString());
                        // Let user know it's been successfully added
                        Toast.makeText(getBaseContext(), "Shopping List successfully created",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }
}
*/