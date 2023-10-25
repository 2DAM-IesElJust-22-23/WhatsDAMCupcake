package com.ieseljust.pmdm.whatsdam.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ieseljust.pmdm.whatsdam.view.MyAdapter
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import com.ieseljust.pmdm.whatsdam.model.mensajesEnviados

class MissatgesViewModel(application: Application) : AndroidViewModel(application) {
    private val _adaptador = MutableLiveData<MyAdapter>().apply {
        value = MyAdapter(
            mensajesEnviados
        ) { m: Mensaje, v: View -> MissatgeLongClickedManager(m, v) }
    }
        val adaptador:MutableLiveData<MyAdapter> =_adaptador

    public fun add(m:Mensaje){
        if (MissatgesRepository.getInstance().add(m)){
            // si ha afegit un element, notifica a l'adaptador que
            // s'ha inserit un element al final.
            // Recordeu que adaptador es un LiveData, i per accedir
            // al seu valor (l'adaptador en si), fem us de value?.
            adaptador.value?.notifyItemInserted(MissatgesRepository.getInstance().getNumMissatges()-1)
            // I com que la vista esta "observant" aquest adaptador,
            // El recyclerView s'actualitzara automaticament
        }
    }

    public fun MissatgeLongClickedManager(contacte:Mensaje, v: View):Boolean {
        // Invoquem el metode remove del contacte, que ens retornara
        // la posicio eliminada, i indicarem a l'adaptador que
        // s'ha eliminat l'element en dita posicio
        val index = MissatgesRepository.getInstance().remove(contacte)
        adaptador.value?.notifyItemRemoved(index)
        // Retornem cert per a que ature la propagacio d'events
        // (i per tant no invoque el gestor d'events del click simple)
        return true
    }

}