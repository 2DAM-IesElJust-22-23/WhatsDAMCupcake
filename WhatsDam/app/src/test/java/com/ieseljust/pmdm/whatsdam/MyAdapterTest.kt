package com.ieseljust.pmdm.whatsdam

import com.ieseljust.pmdm.whatsdam.ViewModels.MyAdapter
import com.ieseljust.pmdm.whatsdam.model.Mensaje
import com.ieseljust.pmdm.whatsdam.repository.MissatgesRepository
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before

class MyAdapterTest {

    @Test
    fun testGetItemViewType() {
        // Crear una instancia de MyAdapter con un nombre de usuario propio
        val adapter = MyAdapter("MiNombreDeUsuario")
        val repository = MissatgesRepository.getInstance()
        val listaMensajes = repository.getMissatges()
        // Recorrer la lista de mensajes y verificar el tipo de vista devuelto por getItemViewType
        for ((index, mensaje) in listaMensajes.withIndex()) {
            val viewType = adapter.getItemViewType(index)

            if (mensaje.usuario == "MiNombreDeUsuario") {
                assertEquals(0, viewType) // Debería ser propio
                println("viewType 0: Mensaje propio ( ${mensaje.mensaje} )")
            } else if (viewType != -1) {
                assertEquals(1, viewType) // Debería ser de otro usuario
                println("viewType 1: Mensaje de otro usuario( ${mensaje.mensaje} )")
            }
        }
    }

    @Before
    fun setUp() {
        // Agregar al menos un mensaje a la lista de mensajes
        val mensaje = Mensaje("MiNombreDeUsuario", "Este es un mensaje de ejemplo", "12:34")
        var mensaje2 = Mensaje("OtroUsuario", "Mensaje de otro usuario", "13:35")
        val repository = MissatgesRepository.getInstance()
        repository.add(mensaje)
        repository.add(mensaje2)
    }
}
