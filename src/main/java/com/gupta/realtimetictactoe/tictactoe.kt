package com.gupta.realtimetictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_tictactoe.*
import java.lang.Exception
import kotlin.random.Random
class tictactoe : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var myemail:String?=null
    var myuid:String?=null
    var database = FirebaseDatabase.getInstance()
    var myRef = database.reference
    var notificationHelper:NotificationHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        var b:Bundle=intent.extras!!
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        notificationHelper= NotificationHelper(this)
        myemail=b.getString("email")
        incommingcall()
        myuid=b.getString("uid")

    }

    fun buclick(view: View) {
        val buselected=view as Button
        var cellid=0
        when(buselected.id){
            R.id.button -> cellid = 1
            R.id.button2 -> cellid = 2
            R.id.button3 -> cellid = 3
            R.id.button4 -> cellid = 4
            R.id.button5 -> cellid = 5
            R.id.button6 -> cellid = 6
            R.id.button7 -> cellid = 7
            R.id.button8 -> cellid = 8
            R.id.button9 -> cellid = 9
        }

        myRef.child("playerOnline").child(sessionId!!).child("boxSelect").child(cellid.toString()).setValue(myemail)
        myRef.child("playerOnline").child(sessionId!!).child("onGame"). child(cellid.toString()).setValue(myemail)

    }
    var activeplayer=1
    var player1=ArrayList<Int>()
    var player2=ArrayList<Int>()
    var win1=0
    var win2=0

    fun buttonclick(cellid: Int, buselected: Button){
        if(activeplayer==1){
            player1.add(cellid)
            buselected.text="x"

            buselected.setBackgroundColor(Color.rgb(175, 224, 206))
            activeplayer=2
        }
        else
        {
            player2.add(cellid)
            activeplayer=1

            buselected.text="O"
            buselected.setBackgroundColor(Color.rgb(197, 216, 109))
        }

        buselected.isEnabled=false
        winner()
    }

    @SuppressLint("ShowToast")
    fun winner()
    {
        var winer=-1
        //row1
        if(player1.contains(1)&&player1.contains(2)&&player1.contains(3))
            winer=1
        //row2
        if(player1.contains(4)&&player1.contains(5)&&player1.contains(6))
            winer=1
        //row3
        if(player1.contains(7)&&player1.contains(8)&&player1.contains(9))
            winer=1
        //col1
        if(player1.contains(3)&&player1.contains(6)&&player1.contains(9))
            winer=1
        //col2
        if(player1.contains(2)&&player1.contains(5)&&player1.contains(8))
            winer=1
        //col3
        if(player1.contains(1)&&player1.contains(4)&&player1.contains(7))
            winer=1
        //row1
        if(player2.contains(1)&&player2.contains(2)&&player2.contains(3))
            winer=2
        //row2
        if(player2.contains(4)&&player2.contains(5)&&player2.contains(6))
            winer=2
        //row3
        if(player2.contains(7)&&player2.contains(8)&&player2.contains(9))
            winer=2
        //col1
        if(player2.contains(3)&&player2.contains(6)&&player2.contains(9))
            winer=2
        //col2
        if(player2.contains(2)&&player2.contains(5)&&player2.contains(8))
            winer=2
        //col3
        if(player2.contains(1)&&player2.contains(4)&&player2.contains(7))
            winer=2
        if(player1.contains(3)&&player1.contains(5)&&player1.contains(7))
            winer=1
        if(player2.contains(3)&&player2.contains(5)&&player2.contains(7))
            winer=2
        if(player1.contains(1)&&player1.contains(5)&&player1.contains(9))
            winer=1
        if(player2.contains(1) &&player2.contains(5)&&player2.contains(9))
            winer=2

        if(winer==1)
        {
            Toast.makeText(this,  "Match ended", Toast.LENGTH_LONG).show()
            win1++
        }else if (winer==2){
            var player2=edmail.text.toString()
            Toast.makeText(this, "PlAYER ${SplitString(player2)} win", Toast.LENGTH_LONG).show()
            win2++
        }else if(winer!=-1&&(player1.size==player2.size))
             {
            Toast.makeText(this,"Match Ended in Draw",Toast.LENGTH_SHORT).show()
              }
        textView2.text="$win1"
        textView3.text="$win2"

    }
    fun autoplay(cellid:Int)
    {

        var buselected:Button?= when(cellid){
            1 -> button
            2 -> button2
            3 -> button3
            4 -> button4
            5 -> button5
            6 -> button6
            7 -> button7
            8 -> button8
            9 -> button9
            else -> button
        }

        buttonclick(cellid, buselected!!)
    }


    fun reset(view: View) {

        val r= Random
        button10.setBackgroundColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)))

        player1.clear()
        player2.clear()
        activeplayer=1
        for (cellid in 1..9) {
            val buselected: Button? = when (cellid) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> button
            }
            buselected!!.text=""
            buselected!!.isEnabled=true
            buselected!!.setBackgroundColor(Color.rgb(249, 249, 249))
          playerOnline(sessionId.toString())
        }
    }

    fun main_color(view: View) {
        var r= Random
        main.setBackgroundColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
    }
    fun buRequest(view: View) {
        var user=edmail.text.toString()
        myRef.child("user").child(SplitString(user)).child("Request").push().setValue(myemail.toString())
        var str= SplitString(myemail.toString())+SplitString(user)
        PlayerSymbol="X"
        playerOnline(str)
    }
    fun buAccept(view: View) {
        var user=edmail.text.toString()
        myRef.child("user").child(SplitString(user)).child("Request").push().setValue(myemail.toString())
        var str= SplitString(user)+SplitString(myemail.toString())
        PlayerSymbol="O"
        playerOnline(str)
    }
    var sessionId:String?=null
    var PlayerSymbol:String?=null
    fun playerOnline(sessionId:String){
        this.sessionId=sessionId
        //        myRef.child("playerOnline").child(sessionId!!).child("onGame").
        myRef.child("playerOnline").child(sessionId).removeValue()
        player1.clear()
        player2.clear()
        myRef.child("playerOnline").child(sessionId).child("onGame")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var td=snapshot.value as HashMap<String,Any>

                        if (td!=null)
                        {
                            for (key in td.keys)
                            {
                               var value=td[key] as String
                                if(value!=myemail)
                                {
                                    activeplayer= if(PlayerSymbol ==="X") 1 else 2
                                }
                                else{
                                    activeplayer= if(PlayerSymbol ==="X") 2 else 1
                                }
                                autoplay(key.toInt())
                                myRef.child("playerOnline").child(sessionId!!).child("onGame").setValue(true)
                            }
                        }
                    }catch (E: Exception){}

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
//        var number=0
//        myRef.child("playerOnline").child(sessionId).child("reset").addValueEventListener(
//            object :ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                 //   notificationHelper.notify(number, notificationHelper!!.notificationReset("RESET","PLease click Reset)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//            }


    }
    fun incommingcall()
    {
        myRef.child("user").child(SplitString(myemail.toString())).child("Request")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var td=snapshot.value as HashMap<String,Any>
                        if (td!=null)
                        {
                            for (key in td.keys)
                            {
                                var value=td[key] as String
                                edmail.setText(value)
                                myRef.child("user").child(SplitString(myemail.toString())).child("Request").setValue("true")
                                break
                            }
                        }
                    }catch (E: Exception){}

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }

    fun  SplitString(str: String):String{
        var split=str.split("@")
        return split[0]
    }


}