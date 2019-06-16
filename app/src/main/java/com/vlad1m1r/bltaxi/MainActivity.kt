package com.vlad1m1r.bltaxi

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.vlad1m1r.bltaxi.domain.model.ItemTaxi
import com.vlad1m1r.bltaxi.taxi.TaxiFragment
import com.vlad1m1r.bltaxi.taxi.TaxiNavigator
import org.koin.android.ext.android.inject

import java.util.ArrayList
import kotlin.math.min

class MainActivity : AppCompatActivity(), TaxiFragment.FragmentHolder {

    private var binding: ViewDataBinding? = null

    private val navigator: TaxiNavigator by inject()

    override fun addShortcuts(itemTaxiArrayList: List<ItemTaxi>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            if (itemTaxiArrayList.isNotEmpty()) {
                val shortcutManager = getSystemService(ShortcutManager::class.java)
                if (shortcutManager != null) {
                    shortcutManager.removeAllDynamicShortcuts()

                    val shortcuts = ArrayList<ShortcutInfo>()

                    for (i in 0 until min(3, itemTaxiArrayList.size)) {
                        val (id, name, phoneNumber) = itemTaxiArrayList[i]

                        val uri = "tel:$phoneNumber"
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(uri)

                        val shortcut = ShortcutInfo.Builder(this, id.toString())
                            .setShortLabel(name)
                            .setLongLabel(name)
                            .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_call))
                            .setIntent(intent)
                            .build()

                        shortcuts.add(shortcut)
                    }

                    shortcutManager.dynamicShortcuts = shortcuts
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
    }

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.nav_host_fragment).navigateUp()

    override fun onResume() {
        super.onResume()
        (navigator as Navigator).bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onPause() {
        super.onPause()
        (navigator as Navigator).unbind()
    }
}
