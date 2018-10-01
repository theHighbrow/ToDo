package com.example.varun.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<Note> notes = new ArrayList<>();
    NotesAdapter notesAdapter = new NotesAdapter(notes);
    BroadcastReceiver br;
    FirebaseDatabase firebaseDatabase;
    Integer uid=0;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.rvNotes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setAdapter(notesAdapter);
        firebaseDatabase=FirebaseDatabase.getInstance();
      //  firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

      /*  if(firebaseUser==null){
            //sign in first
            startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),123);

        }
        else{
            if(firebaseDatabase!=null)
            firebaseDatabase.getReference("note").addChildEventListener(this);
        }
*/
        View dialogView1 = getLayoutInflater().inflate(R.layout.tips, null, false);
      /*  final AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("Tips")
                .setView(dialogView1)
                .create();
        alertDialog1.show();
*/
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_layout, null, false);
        final EditText title = dialogView.findViewById(R.id.editTextTitle);
        final EditText description = dialogView.findViewById(R.id.editTextDescription);
        final EditText time = dialogView.findViewById(R.id.editTextTime);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Enter the note")
                .setCancelable(false)
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Notes note=new Notes("" + System.currentTimeMillis(),"" + title.getText().toString(), "" + description.getText().toString(), ""+time.getText().toString());
                        firebaseDatabase =FirebaseDatabase.getInstance();
                        DatabaseReference reference = firebaseDatabase.getReference("notes").child("Note "+uid);
                        //FirebaseAuth.getInstance().getCurrentUser().getUid()).child(note.getId()
                        reference.setValue(note);
                        uid++;
                      //  notes.add(note);
                        //notesAdapter.notifyItemInserted(notes.size());
                   //     setAlarm(time.getText().toString());
                        // title.setText("");
                        // description.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .create();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        //RETRIEVE USING THIS METHOD
        final DatabaseReference databaseReference= firebaseDatabase.getReference("notes");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note newNote=dataSnapshot.getValue(Note.class);
                notes.add(newNote);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText title = findViewById(R.id.editTextTitle);
        EditText description = findViewById(R.id.editTextDescription);
        RecyclerView recyclerView = findViewById(R.id.rvNotes);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText title = findViewById(R.id.editTextTitle);
        EditText description = findViewById(R.id.editTextDescription);


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAlarm(String string){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 12345, intent, 0);
        Long alarmTime = Long.parseLong(string);
        if(alarmManager!=null)
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+
                    alarmTime*1000, pendingIntent);

    }
}
