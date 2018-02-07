package com.example.varun.todo

/**
 * Created by Varun on 1/6/2018.
 */
data class Notes(val id : String, val title : String, val description : String, val time : String){
  //  constructor(id: String, title: String, description: String, time: Int) : this(id, title, description, time);
  constructor() : this("", "", "", "")

}