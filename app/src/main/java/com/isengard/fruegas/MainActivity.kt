package com.isengard.fruegas

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "FraguasIsengard"


        private const val KEY_ID = "key_id"
        private const val KEY_TIPO = "key_tipo"
        private const val KEY_EQUIPO = "key_equipo"
        private const val KEY_ANTORCHA = "key_antorcha"
        private const val KEY_ERROR = "key_error"
    }

    private lateinit var etIdentificador: EditText
    private lateinit var spTipoUnidad: Spinner
    private lateinit var rgEquipo: RadioGroup
    private lateinit var cbAntorcha: CheckBox
    private lateinit var btnEnviar: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        Log.d(TAG, "onCreate: Saruman ordena construir las fraguas")


        etIdentificador = findViewById(R.id.etIdentificador)
        spTipoUnidad = findViewById(R.id.spTipoUnidad)
        rgEquipo = findViewById(R.id.rgEquipo)
        cbAntorcha = findViewById(R.id.cbAntorcha)
        btnEnviar = findViewById(R.id.btnEnviar)


        etIdentificador.requestFocus()


        etIdentificador.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etIdentificador.text.isNullOrBlank()) {
                etIdentificador.error = getString(R.string.error_anonimo)
            }
        }


        btnEnviar.setOnClickListener { enviarTropa() }


        if (savedInstanceState != null) {
            restaurarEstado(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Las fraguas se encienden")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Los Uruk-hai marchan de nuevo")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Saruman detiene la producción temporalmente")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Las fraguas se apagan en la oscuridad")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Isengard cae ante los Ents")
    }



    private fun enviarTropa() {
        val id = etIdentificador.text.toString().trim()


        if (id.isEmpty()) {
            etIdentificador.error = getString(R.string.error_anonimo)
            etIdentificador.requestFocus()
            return
        }


        val tipo = spTipoUnidad.selectedItem.toString()
        val equipo = findViewById<RadioButton>(rgEquipo.checkedRadioButtonId).text.toString()
        val llevaAntorcha = cbAntorcha.isChecked


        if (!llevaAntorcha) {
            Log.e(TAG, "¡Peligro! Unidad enviada sin fuego")
        }

        Log.d(TAG, "Unidad enviada -> id=$id, tipo=$tipo, equipo=$equipo, antorcha=$llevaAntorcha")

        Toast.makeText(this, getString(R.string.msg_enviada, id), Toast.LENGTH_LONG).show()
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_ID, etIdentificador.text.toString())
        outState.putInt(KEY_TIPO, spTipoUnidad.selectedItemPosition)
        outState.putInt(KEY_EQUIPO, rgEquipo.checkedRadioButtonId)
        outState.putBoolean(KEY_ANTORCHA, cbAntorcha.isChecked)

        outState.putBoolean(KEY_ERROR, etIdentificador.error != null)
        Log.d(TAG, "onSaveInstanceState: Se guardan los planes de guerra")
    }

    private fun restaurarEstado(state: Bundle) {
        etIdentificador.setText(state.getString(KEY_ID, ""))
        spTipoUnidad.setSelection(state.getInt(KEY_TIPO, 0))

        val equipoGuardado = state.getInt(KEY_EQUIPO, R.id.rbArmadura)
        if (equipoGuardado != -1) rgEquipo.check(equipoGuardado)

        cbAntorcha.isChecked = state.getBoolean(KEY_ANTORCHA, false)


        if (state.getBoolean(KEY_ERROR, false)) {
            etIdentificador.error = getString(R.string.error_anonimo)
        }
    }
}