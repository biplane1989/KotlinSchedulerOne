package com.example.kotlinschedulerone

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val jobId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        buttonJobStart.setOnClickListener {
            val componentName = ComponentName(
                this,
                CasarealJobService::class.java
            )

            val jobInfo = JobInfo.Builder(jobId, componentName)
                .apply {
                    setBackoffCriteria(10000, JobInfo.BACKOFF_POLICY_LINEAR);
                    setPersisted(false)
//                        setPeriodic(2000)
                    setMinimumLatency(1000)
                    setOverrideDeadline(2000)
                    setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                    setRequiresCharging(false)
                }.build()
            scheduler.schedule(jobInfo)
        }

        buttonJobCancel.setOnClickListener {
            scheduler.cancel(jobId);

            // 下記の方法だとアプリで登録したすべてのJobがキャンセルされる
            scheduler.cancelAll()
        }
    }
}
