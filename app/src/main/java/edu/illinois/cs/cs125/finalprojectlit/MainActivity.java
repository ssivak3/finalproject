package edu.illinois.cs.cs125.finalprojectlit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;
import com.google.gson.*;
/**
 * Main screen for our API testing app.
 */
public final class MainActivity extends AppCompatActivity {
    /**
     * Default logging tag for messages from the main activity.
     */
    private static final String TAG = "finalprojectlit:Main";

    /**
     * Request queue for our network requests.
     */
    private static RequestQueue requestQueue;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    /**
     * Run when our activity comes into view.
     *
     * @param savedInstanceState state that was saved by the activity last time it was paused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_main);

        // Attach the handler to our UI button
//        final Button startAPICall = findViewById(R.id.startAPICall);
//        startAPICall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                Log.d(TAG, "Start API button clicked");
//                startAPICall();
//            }
//        });

        // Make sure that our progress bar isn't spinning and style it a bit
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

    }

    /**
     * Make an API call.
     */
    void startAPICall(final View v) {
        try {
            //retrieves user input
            EditText editText = (EditText) findViewById(R.id.editText);
            String place = editText.getText().toString();
            //final int limit = 10;
            //calls API
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.foursquare.com/v2/venues/explore?near="+place+"&limit=10&client_id=4PQSM3203ZN13XY2GJCTH2ZBPU43KZGJXNJ4YOLDJ4OX4GDM&client_secret=VQP3TG3GKJPBHWYSASZJ1PCAZJFRBPGGW1VPEUXV0FJ2YYIX&v=20180427",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            final TextView find = findViewById(R.id.jsonResult);
                            try {
                                String results = "";
                                String responseString = response.toString();
                                JsonParser parser = new JsonParser();
                                JsonObject parsedJson = parser.parse(responseString).getAsJsonObject();
                                JsonObject jsonresponse = parsedJson.getAsJsonObject("response").getAsJsonObject();
                                JsonObject groups = jsonresponse.getAsJsonArray("groups").get(0).getAsJsonObject();

                                for (int i = 0; i < 10; i++) {

                                    JsonObject items = groups.getAsJsonArray("items").get(i).getAsJsonObject();
                                    JsonObject venue = items.getAsJsonObject("venue").getAsJsonObject();
                                    JsonObject location = venue.getAsJsonObject("location").getAsJsonObject();
                                    String address = location.get("address").getAsString();
                                    String name = venue.get("name").getAsString();

                                    results = results + "\n"+ name + ": " + address;
                                }
                                find.setText(results);
                                Log.d(TAG, results);
                            } catch (Exception e) {
                                find.setText("internal error");
                                Log.d(TAG, "internal error");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}



        /**
         * Run when our activity comes into view.
         *
         * @param savedInstanceState state that was saved by the activity last time it was paused
         */
       /** @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_message);

            // Get the Intent that started this activity and extract the string
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.textView);
            textView.setText(message);
//        startAPICall();
        }
    }


    /** Called when the user taps the getlit button */
   /** public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

} */
