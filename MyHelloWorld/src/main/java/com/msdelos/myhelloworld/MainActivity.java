package com.msdelos.myhelloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences;

        final Button addBtn = (Button) findViewById(R.id.button);
        final EditText nameText = (EditText) findViewById(R.id.editName);

        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(!nameText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Resources res = getResources();

            Context context = getApplicationContext();
            String text = String.format(res.getString(R.string.Sgreeting), nameText.getText());
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            }
        });

        preferences = getPreferences(MODE_PRIVATE);
        if (preferences.contains("UserName"))
            nameText.setText(preferences.getString("UserName", null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Setting is not implemented yet!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.quit:
                finish();
                return true;
            default:
                break;
        }

        return false;
    }

    protected void onPause() {
        super.onPause();

        if (!isFinishing())
            return;

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        final EditText nameText = (EditText) findViewById(R.id.editName);

        if (!nameText.getText().toString().trim().isEmpty())
           editor.putString("UserName", nameText.getText().toString());
        else
            editor.remove("UserName");
        editor.commit();

        Toast.makeText(getApplicationContext(), "Saving user... .", Toast.LENGTH_LONG).show();
    }

    /*
     * onSaveInstanceState y onRestoreInstanceState:
     * Se usan para guardar el estado de la ventana. Cuando rotas la pantalla, por ejemplo, android "destrulle" la activity, y la crea nuevamente.
     * Estos cambios (putStrign(), etc)no son persistentes!! !!. Se destruyen al cerrar definitivamente la aplicacion.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        final EditText nameText = (EditText) findViewById(R.id.editName);

        if (!nameText.getText().toString().trim().isEmpty())
            savedInstanceState.putString("UserName", nameText.getText().toString());
        else
            savedInstanceState.remove("UserName");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final EditText nameText = (EditText) findViewById(R.id.editName);

        if (savedInstanceState.containsKey("UserName"))
            nameText.setText(savedInstanceState.getString("UserName"));
    }
}
