package com.example.workout

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.workout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.util.*

class FinishActivity : AppCompatActivity() {
   // private var mProgressListener:UtteranceProgressListener?=null

    private var binding:ActivityFinishBinding?=null
   // private var tts: TextToSpeech?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
       // tts= TextToSpeech(this,this)
//        tts?.setOnUtteranceProgressListener(mProgressListener)
        //speakOut("Congratulations, workout completed")
//        if (binding?.tvsuccess?.text!!.isEmpty()){
//            Toast.makeText(this,"CONGRATULATIONS",Toast.LENGTH_SHORT).show()
//        }
//        else{
//            speakOut(binding?.tvsuccess?.text.toString())
//        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val dao=(application as WorkOutApp).db.historyDao()
        addDateToDatabase(dao)

    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        val c = Calendar.getInstance() // Calendars Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date)) // Add date function is called.
            Log.e(
                "Date : ",
                "Added..."
            ) // Printed in log which is printed if the complete execution is done.
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//        if(tts!=null){
//            tts!!.stop()
//            tts!!.shutdown()
//        }
//
//        binding=null
//    }
//
//
//    override fun onInit(status: Int) {
//        if(status==TextToSpeech.SUCCESS){
//            val result=tts?.setLanguage(Locale.US)
//            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
//                Log.e("TTS","The language specified is not supported!")
//            }
//        }
//        else{
//            Log.e("TTS","Initialisation failed!")
//        }
//    }
//
//    private fun speakOut(text:String){
//        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
//    }


}
