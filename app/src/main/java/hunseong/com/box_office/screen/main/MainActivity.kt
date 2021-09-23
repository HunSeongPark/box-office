package hunseong.com.box_office.screen.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import hunseong.com.box_office.R
import hunseong.com.box_office.databinding.ActivityMainBinding
import hunseong.com.box_office.screen.base.BaseActivity
import hunseong.com.box_office.screen.my.MyFragment
import hunseong.com.box_office.screen.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavigationBarView.OnItemSelectedListener {

    override val viewModel by viewModel<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        viewModel.getName()
        bottomNavigationView.setOnItemSelectedListener(this@MainActivity)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_movie -> {
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
                true
            }
            R.id.menu_my -> {
                showFragment(MyFragment.newInstance(), MyFragment.TAG)
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, tag)
            .commitAllowingStateLoss()
    }

    override fun observeData() = viewModel.nameLiveData.observe(this) {
        if (it == "unknown") {
            showNameDialog()
        } else if (it == null) Unit
        else showFragment(HomeFragment.newInstance(), HomeFragment.TAG)

    }

    private fun showNameDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null)
        val editText: EditText = view.findViewById(R.id.editText)
        val lengthTextView: TextView = view.findViewById(R.id.lengthTextView)
        val dialog = AlertDialog.Builder(this)
            .setTitle("프로필 이름 설정")
            .setView(view)
            .setPositiveButton("설정", null)
            .setCancelable(false)
            .create()

        editText.addTextChangedListener {
            lengthTextView.text = "(${editText.length()}/10)"
            if (editText.length() >= 10) {
                lengthTextView.setTextColor(getColor(R.color.dark_red))
            } else {
                lengthTextView.setTextColor(Color.BLACK)
            }
        }

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTextColor(getColor(R.color.dark_gray))

            positiveButton.setOnClickListener {
                if (editText.text.isNullOrBlank().not()) {
                    viewModel.setName(editText.text.toString())
                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()

    }
}