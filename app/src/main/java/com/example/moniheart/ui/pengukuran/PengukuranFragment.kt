package com.example.moniheart.ui.pengukuran

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moniheart.databinding.FragmentPengukuranBinding
import com.example.moniheart.ui.hasil.HasilActivity
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class PengukuranFragment : Fragment() {

    private var _binding: FragmentPengukuranBinding? = null
    private  lateinit var timer: CountDownTimer
    lateinit var ref:DatabaseReference
    lateinit var btnPengukuran: Button
    lateinit var tvPengukuran: TextView
    lateinit var  calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date: String
    lateinit var tv_timer: TextView
    var hasilbpm: Int = 0

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(PengukuranViewModel::class.java)
        _binding = FragmentPengukuranBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        ref = FirebaseDatabase.getInstance().getReference("bpm")
        btnPengukuran = binding.btnPengukuran
        tvPengukuran = binding.tvPengukuran
        tv_timer = binding.tvTimer

        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        date = simpleDateFormat.format(calendar.time)


        btnPengukuran.setOnClickListener {
            ref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                            var bpm = snapshot.getValue().toString()
                            if (bpm != null){
                                tvPengukuran.text = bpm
                                var kondisi = "Lemah"
                                hasilbpm = bpm.toInt()

                            }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            timer = object: CountDownTimer(30000, 1){
                override fun onTick(remaining: Long) {
                    tv_timer.text = remaining.toString()
                }

                override fun onFinish() {
                    tv_timer.text = "Selesai"
                }
            }

            timer.start()

            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    val intent = Intent(activity, HasilActivity::class.java)
                    intent.putExtra("bpm", hasilbpm)
                    //intent.putExtra("kondisi", kondisi)
                    intent.putExtra("waktu", date)
                    activity!!.startActivity(intent)
                }
            }, 30000)
        }

        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}