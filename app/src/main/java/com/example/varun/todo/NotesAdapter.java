package com.example.varun.todo;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Varun on 1/6/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    ArrayList<Notes> notesArrayList;
    NotesAdapter(ArrayList<Notes> notes){
        notesArrayList=notes;
    }//this?

    @Override
    public NotesAdapter.NotesHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
       Context context = parent.getContext();
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.item_row,parent, false);
        NotesHolder notesHolder = new NotesHolder(inflatedView);
        return notesHolder;

    }

    @Override
    public void onBindViewHolder(final NotesHolder holder, int position) {
        Notes currentNote = notesArrayList.get(position);
   //    if(notesArrayList.get(position.getDone())){
           //strikethrough
           //text setting
    //   }else{
           //normal flow
      // }
        holder.title.setText(currentNote.getTitle());
        holder.description.setText(currentNote.getDescription());
        holder.time.setText(currentNote.getTime());
        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notesArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                return true;    //what does this true or false mean?
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      remoteviews.setInt(R.id.YourTextView, "setPaintFlags", Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                  //TextView title= ;
                 // title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                //tv = inflatedView.findViewById(R.id.textViewRow);
                //tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }




    public class NotesHolder extends RecyclerView.ViewHolder{
            TextView title;
            TextView description;
            TextView time;
        public NotesHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewRow);
            description= itemView.findViewById(R.id.textViewRow2);
            time=itemView.findViewById(R.id.textViewRow3);
        }
    }
}
