package btu.ninidze.stepcounter.ui.main.count

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.hilt.work.HiltWorkerFactory
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import btu.ninidze.stepcounter.R
import btu.ninidze.stepcounter.data.MyBroadcastReceiver
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.worker.StepCountWorker
import btu.ninidze.stepcounter.data.worker.StepWorker
import btu.ninidze.stepcounter.databinding.FragmentStepBinding
import btu.ninidze.stepcounter.ui.BaseFragment
import btu.ninidze.stepcounter.util.NotificationUtil
import btu.ninidze.stepcounter.util.NotificationUtil.CHANNEL_ID
import btu.ninidze.stepcounter.util.extensions.getDominantSwatch
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class StepFragment : BaseFragment<FragmentStepBinding>() {

    private val viewModel by viewModels<StepViewModel>()

    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStepBinding
        get() = FragmentStepBinding::inflate

    override fun init() = with(binding) {
        initWorker()
        viewModel.getUser()
        viewModel.getUsersSneakers()

        tvName.setOnClickListener {
            viewModel.getMoney(
                "69420"
            )
        }

        viewModel.getMoney.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success ->  {
                    val data = it.data
                    tvMoney.text = data.money

                }
                is Resource.Error -> {  }
            }
        }

        viewModel.getUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    val data = response.data
                    tvSteps.text = (viewModel.totalSteps.value ?: 0).toString()
                    tvMoney.text = data.money

                }
                is Resource.Error -> {}
            }
        }

        viewModel.getUsersSneakers.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.data.isEmpty()) return@observe
                    val data = response.data.first()
                    tvName.text = data.name
                    ivSneaker.getDominantSwatch(data.imageUrl) { palette ->
                        if (palette == null) return@getDominantSwatch
                        root.setBackgroundColor(
                            palette.dominantSwatch?.rgb ?: R.color.secondary_color
                        )
                    }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun initWorker() {

        createNotificationChannel()
        NotificationUtil.showSimpleNotification(requireContext())

        val worker = PeriodicWorkRequestBuilder<StepWorker>(10, TimeUnit.SECONDS)
            .addTag("TAG_OUTPUT")
            .build()
        WorkManager.getInstance(requireContext().applicationContext)
            .enqueue(worker)

        WorkManager.getInstance(requireContext().applicationContext)
            .getWorkInfosByTagLiveData("TAG_OUTPUT")
            .observe(viewLifecycleOwner) { workInfos ->
                if (workInfos.isNullOrEmpty()) return@observe
                val workInfo = workInfos[0]
                if (workInfo.state.isFinished) {
                    val step = workInfo.progress.getInt("steps", 0)
                    println(step)
                    binding.tvSteps.text = step.toString()
                }
            }

        binding.tvSteps.setOnClickListener {
            WorkManager.getInstance(requireContext()).cancelAllWork()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "agarari"
            }
            val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}