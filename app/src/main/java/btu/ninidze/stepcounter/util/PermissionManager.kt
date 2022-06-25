package btu.ninidze.stepcounter.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

typealias PermissionListener = (granted: Boolean) -> Unit

abstract class PermissionManager(private val context: Context) {
    protected var permissionListener: PermissionListener? = null

    abstract val permissionResultLauncher: ActivityResultLauncher<String>

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat
            .checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(
        permissions: String,
        permissionListener: PermissionListener
    ) {
        this.permissionListener = permissionListener

        if (hasPermission(permissions)) {
            permissionListener.invoke(true)
        } else {
            permissionResultLauncher.launch(permissions)
        }
    }

}

class FragmentPermissionManager @Inject constructor(
    fragment: Fragment,
    @ActivityContext context: Context
) : PermissionManager(context) {
    override val permissionResultLauncher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { hasGranted ->
            permissionListener?.invoke(hasGranted)
        }
}