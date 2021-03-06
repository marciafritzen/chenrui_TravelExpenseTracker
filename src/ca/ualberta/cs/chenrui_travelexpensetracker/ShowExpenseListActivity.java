package ca.ualberta.cs.chenrui_travelexpensetracker;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ShowExpenseListActivity extends ShowClaimListActivity {
	
	private ExpenseAdapter showExpenseListViewAdapter;
	private ListView showExpenseListView;
	
	private TextView showExpenseStartDateTtextView;
	private TextView showExpenseEndDateTextView;
	private TextView showExpenseDecriptionTextView;
	private TextView showExpensTotalTextView;
	
	public static int OpenedExpensePosition;
	public static boolean isAExpenseOpened = false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_expense_list);
		
		// set title
		setTitle(OpenedClaim.toString()+" ("+OpenedClaim.getStatus()+")");
		
		// create temple view
		showExpenseStartDateTtextView = (TextView) findViewById(R.id.showExpenseStartDateTtextView);
		showExpenseEndDateTextView = (TextView) findViewById(R.id.showExpenseEndDateTextView);
		showExpenseDecriptionTextView = (TextView) findViewById(R.id.showExpenseDecriptionTextView);
		showExpensTotalTextView = (TextView) findViewById(R.id.showExpensTotalTextView);
		
		Button showExpenseAddExpenseButton = (Button) findViewById(R.id.showExpenseAddExpenseButton);
		Button showExpenseEditClaimButton = (Button) findViewById(R.id.showExpenseEditClaimButton);
		Button showExpenseEmailButton = (Button) findViewById(R.id.showExpenseEmailButton);
		
		//ListView showExpenseListView = (ListView) findViewById(R.id.showExpenseListView);
		showExpenseListView = (ListView) findViewById(R.id.showExpenseListView);
		
		
		// put data into test view
		showExpenseStartDateTtextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(OpenedClaim.getStartDate()));
		showExpenseEndDateTextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(OpenedClaim.getEndDate()));
		showExpenseDecriptionTextView.setText(OpenedClaim.getDescription());
		
		
		// set up button listener
		showExpenseAddExpenseButton.setOnClickListener(new ButtonListener());
		showExpenseEditClaimButton.setOnClickListener(new ButtonListener());
		showExpenseEmailButton.setOnClickListener(new ButtonListener());
		
		//http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event 1.26.2015.
		showExpenseListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				OpenedExpensePosition = position;
				
				// setForEditExpense(claims.get(forDetailExpenseIndex));
				Expense forDetailExpense = OpenedClaim.getExpenseList().get(
						position);

				// open an info dialog
				AlertDialog.Builder adb = new AlertDialog.Builder(
						ShowExpenseListActivity.this);

				// http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
				// 2015.1.30.
				LayoutInflater factory = LayoutInflater
						.from(ShowExpenseListActivity.this);
				View expenseInfoDialogView = factory.inflate(
						R.layout.expense_dialog, null);
				adb.setView(expenseInfoDialogView);

				// set title
				adb.setMessage("        Expense Details");

		        //set up the original info
		        TextView expenseDialogItemInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogItemInfo);
		        TextView expenseDialogAmountInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogAmountInfo);
		        TextView expenseDialogDateInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogDateInfo);
		        TextView expenseDialogCategoryInfo=(TextView) expenseInfoDialogView.findViewById(R.id.expenseDialogCategoryInfo);
		        TextView expenseDialogDecriptionInfo=(TextView)  expenseInfoDialogView.findViewById(R.id.expenseDialogDecriptionInfo);
		            	
				/*
				 * System.out.println(forDetailExpense.getItem());
				 * System.out.println(expenseDialogItemInfo);
				 */
		            	
		        expenseDialogItemInfo.setText(forDetailExpense.getItem());
		        expenseDialogAmountInfo.setText(forDetailExpense.getCurrency().toString());
		        expenseDialogDateInfo.setText(forDetailExpense.getDate().toString());
		        expenseDialogCategoryInfo.setText(forDetailExpense.getCategory());
		        expenseDialogDecriptionInfo.setText(forDetailExpense.getDescription());
		            	/*
						adb.setMessage("Item: "+forDetailExpense.getItem()+"\n\n"
								+" "+forDetailExpense.getCurrency()+" "+forDetailExpense.getAmount()+"\n\n"
								+"Date:  "+forDetailExpense.getDate().toString()+"\n\n"+
								"Category: "+forDetailExpense.getCategory()+"\n\n"+
								"Description: "+forDetailExpense.getDescription());*/
		            	
		            	//adb.setTitle("Expense Details");
				adb.setCancelable(true);
						
				adb.setPositiveButton("Edit", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(ShowExpenseListActivity.this,AddExpenseActivity.class);
						isAExpenseOpened = true;
						startActivity(intent);
						}
					});
				adb.show();
						
						
				//adapter.notifyDataSetChanged();
						
				//saveInFile(null, new Date(System.currentTimeMillis()));
						
				//Toast.makeText(getBaseContext(), item, 1).show();
		        //Toast.cancel();
				//Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
				//System.out.println(getForEditExpense());
		    }
		});
				
				
				
		//https://github.com/jeremykid/Weijie2_Travel-expense-tracking/blob/master/src/ca/ualberta/cs/travel/MainActivity.java 2015.1.29.
		showExpenseListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView,View view, int position, long id) {
				final int deleteIndex = position;

				AlertDialog.Builder adb = new AlertDialog.Builder(ShowExpenseListActivity.this);
				adb.setMessage("Delete " + "\""	+ OpenedClaim.getExpenseList().get(position).getItem() + "\"" + "?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					// pull data out
					ArrayList<Expense> deleteExpenseList = OpenedClaim.getExpenseList();
					Currency deleteExpenseCurrency = deleteExpenseList.get(deleteIndex).getCurrency();
					deleteExpenseCurrency.setAmount(0-deleteExpenseCurrency.getAmount());
					// prepare for saving new data
					deleteExpenseList.remove(deleteIndex);
					OpenedClaim.addToTotal(deleteExpenseCurrency);
					OpenedClaim.setExpenseList(deleteExpenseList);
					// saving new data
					ShowClaimListActivity.ClaimList.remove(ShowClaimListActivity.OpenedClaimPosition);
					ShowClaimListActivity.ClaimList.add(ShowClaimListActivity.OpenedClaimPosition,ShowClaimListActivity.OpenedClaim);
					// change the display
					showExpensTotalTextView.setText(ShowClaimListActivity.OpenedClaim.TotalCurrencyListToString());
					showExpenseListViewAdapter.notifyDataSetChanged();
					saveInFile();
					}
				});
				adb.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					
				}
				});
				// Toast.makeText(ListStudentsActivity.this,
				// "Is the on click working?",
				// Toast.LENGTH_SHORT).show();
				adb.show();

				return true;
			}
		});
	}
	
	// reaction of Button
	class ButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			if (view.getId() == R.id.showExpenseAddExpenseButton){
				Intent intent = new Intent(ShowExpenseListActivity.this,
						AddExpenseActivity.class);
				startActivity(intent);
			} else if (view.getId() == R.id.showExpenseEditClaimButton) {
				Intent intent = new Intent(ShowExpenseListActivity.this,
						AddClaimActivity.class);
				startActivity(intent);
			} else if (view.getId() == R.id.showExpenseEmailButton){
				//Email things
			}
		}
	}

	// set up claim listing adapter
	@Override
	protected void onStart() {
		super.onStart();
		isAClaimOpened = true;
		isAExpenseOpened = false;
		
		showExpenseListViewAdapter = new ExpenseAdapter(this, R.layout.list_expense,ShowClaimListActivity.OpenedClaim.getExpenseList());
		showExpenseListView.setAdapter(showExpenseListViewAdapter);
		showExpenseListViewAdapter.notifyDataSetChanged();
		
		
		showExpensTotalTextView.setText(ShowClaimListActivity.OpenedClaim.TotalCurrencyListToString());
		showExpenseStartDateTtextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(ShowClaimListActivity.OpenedClaim.getStartDate()));
		showExpenseEndDateTextView.setText((new SimpleDateFormat ("yyyy.MM.dd")).format(ShowClaimListActivity.OpenedClaim.getEndDate()));
		showExpenseDecriptionTextView.setText(OpenedClaim.getDescription());
		
		//System.out.println("I'm here.");
	}
	
	// custom adapter
	public class ExpenseAdapter extends ArrayAdapter<Expense> {
		public ExpenseAdapter(Context context, int resource,
				List<Expense> objects) {
			super(context, resource, objects);
		}

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       // Get the data item for this position
			Expense expense = getItem(position);    
	       // Check if an existing view is being reused, otherwise inflate the view
	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_expense, parent, false);
	       }
	       // Lookup view for data population
	       TextView eventName = (TextView) convertView.findViewById(R.id.eventListElementNameView);
	       TextView eventAmount = (TextView) convertView.findViewById(R.id.eventListElementAmountView);
	       TextView eventGap = (TextView) convertView.findViewById(R.id.eventListElementGapView);
	       TextView eventDate = (TextView) convertView.findViewById(R.id.eventListElementDateView);
	       TextView eventYear = (TextView) convertView.findViewById(R.id.eventListElementYearView);
	       
	       eventDate.setText(adapterDateInFormatForExpenseList(expense.getDate()));
	       eventYear.setText(""+((expense.getDate()).getYear()+1900));
	       eventName.setText(expense.getItem());
	       eventAmount.setText(""+expense.getAmount());
	       eventGap.setText(" "+expense.getCurrency().getType());
	       
	       return convertView;
	   }
		
		public String adapterDateInFormatForExpenseList(Date date){
			String formatDate = "";
			formatDate += ""+new DateFormatSymbols().getShortMonths()[date.getMonth()];
			formatDate += " "+date.getDate();
			//formatDate += ",\n        "+(date.getYear()+1900);
			return formatDate;
		}
		
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_expense_list, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.showExpenseToTextView) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
