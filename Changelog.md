# Registre de canvis

## [Versió 1.0.0] - 30-09-2023

### Afegit
- Funcionalitat A: Finestra de registre, lògica per a comprobar que Nickname no estiga buit. I que la adreça del servidor siga una IP correcta. Que guarde el estat d'adreça e IP.

- Funcionalitat B: Finestra de Missatges, es mostra el Nickname i l'adreça del servidor, obitnguts de la primera finestra, emmagatzemats a l'intent. Al fer click al botó d'enviar, es restaura a cade buida.


### Canviat


### Eliminat

## [Versió 1.0.1] - 15-10-2023

### Afegit
- Funcionalitat: S'ha afegit la finestra de missatges amb RecyclerView per a veure els missatges que manem.


### Canviat
- S'han fet privats les variables mensaje_texto i hora de MensajeViewHolder.kt

### Eliminat

## [Versió 1.0.2] - 16-10-2023

### Afegit

### Canviat
- S'ha solucionat l'error que causava que els missatges s'eliminaren. S'ha mogut la llista i la classe al fitxer "mensaje.kt".
- S'ha afegit que es notifique a l'adaptador de "MensajesRecyclerView" quan afegim un nou element a la llista per actualitzar-la.
- S'ha afegit que la vista del RecyclerView es desplace cap a l'última posició de la llista per a mostrar l'últim missatge.

### Eliminat


