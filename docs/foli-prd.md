# PRD — App de hábito de lectura

## 1. Resumen

Aplicación móvil personal enfocada en ayudar a crear y mantener el hábito de la lectura mediante sesiones cortas, metas simples, seguimiento de progreso y gamificación ligera.

La aplicación también permite guardar ideas o reflexiones de manera opcional y, cuando tenga sentido, convertirlas en acciones concretas.

El producto debe funcionar igualmente bien para:

- Novelas.
- Libros de autoayuda.
- Libros académicos.
- Libros técnicos.
- Ensayos.
- Otros tipos de lectura.

La app no busca obligar al usuario a aprender algo de cada libro. El objetivo principal es **leer de forma constante**.

---

## 2. Problema

El usuario tiene libros pendientes que quiere leer, pero encuentra principalmente estas dificultades:

- Falta de motivación para empezar.
- Empieza libros y posteriormente los abandona.
- Leer puede sentirse como una actividad que requiere demasiado esfuerzo.
- No existe una sensación clara de avance a corto plazo.
- En libros orientados al aprendizaje, muchas ideas interesantes terminan olvidándose.
- El seguimiento excesivo puede convertir la lectura en otra tarea.

La solución debe reducir la fricción entre:

**“Quiero leer” → “Estoy leyendo”.**

---

## 3. Objetivo principal

Ayudar al usuario a crear constancia en la lectura y terminar más libros.

El producto debe conseguir que sea fácil:

1. Empezar una sesión.
2. Leer una pequeña cantidad.
3. Ver progreso inmediatamente.
4. Sentir motivación para regresar al día siguiente.

Como objetivo secundario:

> Permitir conservar y aplicar conocimiento cuando el tipo de lectura lo amerite.

---

## 4. Principios del producto

### Leer primero

La función principal siempre es leer.

Registrar información, escribir reflexiones o crear acciones nunca debe convertirse en requisito para completar una sesión.

### Baja fricción

Desde abrir la aplicación hasta comenzar una sesión debe haber muy pocas interacciones.

Idealmente:

**Abrir app → Continuar leyendo → Empezar.**

### Gamificación tranquila

La aplicación debe motivar sin sentirse competitiva, infantil o demandante.

Referencias de sensación:

- Headspace.
- Forest.

Debe transmitir calma, progreso y satisfacción.

### Experiencia personal

No habrá:

- Comunidad.
- Seguidores.
- Rankings.
- Comparaciones con otros usuarios.
- Actividad pública.

La lectura se considera una experiencia privada.

### IA discreta

La IA no será protagonista del producto.

Solo aparecerá cuando exista una utilidad clara para el usuario.

---

# 5. Plataforma

Aplicación móvil para:

- Android.
- iOS.

La aplicación será **mobile-first**.

No se considera necesario desarrollar inicialmente:

- Aplicación web.
- Aplicación de escritorio.

La arquitectura debería permitir sincronización en el futuro, aunque el MVP no requiere cuenta.

---

# 6. Concepto principal

Cada libro tendrá una **intención de lectura**.

Cuando el usuario agrega un libro puede elegir:

### 🌿 Solo leer

Quiero disfrutar el libro y mantener mi hábito.

Ideal para novelas o lectura recreativa.

### 💭 Leer y recordar

Quiero guardar ideas, citas o reflexiones interesantes.

### ⚡ Leer y aplicar

Quiero guardar ideas y convertir algunas de ellas en acciones.

La intención podrá modificarse posteriormente.

Esto permite que la aplicación no trate todos los libros de la misma manera.

---

# 7. Loop principal

El ciclo principal del producto será:

**Elegir libro**

↓

**Definir meta**

↓

**Leer**

↓

**Registrar progreso**

↓

**Celebrar sesión**

↓

**Opcional: guardar una idea**

↓

**Opcional: convertir la idea en acción**

↓

**Regresar al día siguiente**

El usuario debe poder completar una sesión sin escribir ninguna nota.

---

# 8. Funcionalidades del MVP

## 8.1 Biblioteca simple

El usuario podrá tener:

### Leyendo

Máximo **2 libros activos simultáneamente**.

### Pendientes

Libros que quiere leer posteriormente.

No se necesita inicialmente una biblioteca compleja con múltiples categorías.

---

## 8.2 Agregar libros

El usuario podrá buscar un libro mediante un buscador.

Información mínima:

- Portada.
- Título.
- Autor.
- Número de páginas.

Al agregarlo podrá elegir su intención:

- Solo leer.
- Leer y recordar.
- Leer y aplicar.

---

# 9. Sesiones de lectura

Las sesiones serán el corazón de la aplicación.

El usuario definirá su meta de lectura en páginas.

Ejemplos:

- 5 páginas.
- 10 páginas.
- 15 páginas.
- Cantidad personalizada.

La aplicación no establecerá automáticamente cuánto debe leer el usuario.

La pantalla de sesión debe ser sencilla y libre de distracciones.

Debe mostrar como mínimo:

- Libro.
- Meta de páginas.
- Progreso de la sesión.

---

# 10. Cierre de sesión

Al terminar una sesión, el usuario registra hasta qué página llegó.

La aplicación muestra inmediatamente el resultado.

Ejemplo:

> 🎉 10 páginas completadas  
> 34% → 38%  
> 🔥 Racha de 8 días

Después de esto la sesión ya está considerada completada.

Cualquier interacción adicional será opcional.

---

# 11. Progreso del libro

El progreso principal se mostrará mediante porcentaje.

Ejemplo:

> Atomic Habits  
> 38%

El usuario debe poder entender rápidamente cuánto ha avanzado sin necesidad de consultar estadísticas complejas.

---

# 12. Rachas

La aplicación utilizará rachas para incentivar la constancia.

Ejemplo:

> 🔥 12 días leyendo

Una sesión válida durante el día mantiene la racha.

## Recuperación de racha

Romper una racha no debe generar una sensación de fracaso definitivo.

Cuando se pierde, la aplicación puede ofrecer una misión de recuperación.

Ejemplo:

> Tu racha de 14 días terminó.

> 🔥 Lee durante 3 días consecutivos para recuperarla.

Si completa el objetivo, recupera la racha anterior y continúa desde allí.

La recuperación debe sentirse como una segunda oportunidad, no como un castigo.

---

# 13. Logros

La aplicación tendrá pequeños logros relacionados con el progreso.

Ejemplos:

- Primera sesión.
- Primera semana leyendo.
- 7 días consecutivos.
- 25% de un libro.
- 50% de un libro.
- Primer libro terminado.
- Primera idea guardada.
- Primera idea aplicada.

Los logros deben complementar la experiencia, no dominarla.

---

# 14. Ideas y reflexiones

Después de una sesión puede aparecer:

> ¿Quieres guardar algo de esta lectura?

Opciones:

- Guardar idea.
- Ahora no.

El usuario podrá escribir una pequeña reflexión.

Para el MVP todas se considerarán simplemente:

**Ideas**

No será necesario distinguir entre:

- Citas.
- Reflexiones.
- Notas.
- Aprendizajes.

Ejemplos:

### Novela

> Creo que este personaje está ocultando algo.

### Libro técnico

> Separar responsabilidades reduce el acoplamiento.

### Productividad

> Reducir la fricción facilita iniciar un hábito.

---

# 15. Convertir ideas en acciones

En libros configurados como **Leer y aplicar**, una idea podrá convertirse en acción.

Flujo:

**Idea**

↓

**Convertir en acción**

↓

**Definir acción**

↓

**Realizar seguimiento**

↓

**Evaluar resultado**

Para el MVP:

**Una idea → máximo una acción.**

---

# 16. IA en el MVP

La única función de IA inicial será ayudar a transformar una idea en una acción concreta.

Ejemplo:

### Idea

> Preparar el entorno ayuda a mantener un hábito.

### Sugerencia de IA

> Durante los próximos 5 días deja el libro sobre tu escritorio antes de dormir.

El usuario siempre podrá:

- Editarla.
- Aceptarla.
- Ignorarla.

La IA trabajará únicamente con las notas que el usuario haya introducido.

No tendrá acceso al contenido completo del libro.

---

# 17. Seguimiento de acciones

Una acción puede tener estados simples:

- Pendiente.
- En curso.
- Aplicada.
- Descartada.

Cuando corresponda puede tener una duración.

Ejemplo:

> Durante 5 días dejaré preparado el libro antes de dormir.

La aplicación podrá preguntar diariamente:

> ¿Lo hiciste hoy?

Al finalizar:

> ¿Te funcionó?

Opciones:

- Sí.
- Parcialmente.
- No.

El objetivo es cerrar el ciclo:

**Leer → descubrir → probar → evaluar.**

---

# 18. Pantalla principal

La pantalla principal debe responder inmediatamente:

> ¿Qué debería hacer ahora?

Elementos principales:

### Libro actual

Portada, título y progreso.

### Continuar leyendo

Acción principal de la pantalla.

### Meta actual

Ejemplo:

> Hoy: 10 páginas.

### Racha

Ejemplo:

> 🔥 8 días.

### Acción pendiente

Si existe una acción activa relacionada con algún aprendizaje.

No debe convertirse en un dashboard de estadísticas.

---

# 19. Finalizar un libro

Cuando el usuario alcanza el 100%, la aplicación celebra el logro.

Ejemplo:

> 🎉 Terminaste Atomic Habits.

La aplicación puede preguntar:

> ¿Qué vas a aplicar de este libro?

Esta reflexión será opcional.

El libro pasa posteriormente al historial de libros terminados.

---

# 20. Recordatorios

Los recordatorios serán importantes para construir el hábito.

El usuario podrá configurar un horario.

Las notificaciones deben ser pocas y respetuosas.

Ejemplo:

> 📖 Tu libro te espera. ¿10 páginas hoy?

No debe existir spam de notificaciones ni mensajes constantes para presionar al usuario.

---

# 21. Experiencia visual

La aplicación debe transmitir:

- Calma.
- Progreso.
- Motivación.
- Cercanía.
- Diversión ligera.

Inspiraciones:

**Headspace + Forest**

Características deseadas:

- Colores agradables.
- Ilustraciones sencillas.
- Animaciones suaves.
- Recompensas visuales.
- Interacciones rápidas.
- Poco texto innecesario.

La gamificación puede utilizar conceptos relacionados con crecimiento, como plantas o elementos visuales que evolucionen junto al progreso.

---

# 22. Privacidad

La lectura debe sentirse privada.

Por eso el MVP no tendrá:

- Perfil público.
- Amigos.
- Seguidores.
- Rankings.
- Feed.
- Compartir automáticamente actividad.

La información del usuario pertenece al usuario.

---

# 23. Fuera del MVP

Las siguientes funcionalidades quedan para versiones posteriores:

- Widget para iniciar lectura.
- Notas mediante voz.
- OCR de páginas.
- Fotografiar citas.
- Flashcards.
- Búsqueda global de ideas.
- Revisión inteligente de aprendizajes.
- Mapas de conocimiento.
- Estadísticas avanzadas.
- Recomendaciones de libros.
- Comunidad.
- Sincronización entre dispositivos.
- Aplicación web.
- Aplicación de escritorio.
- IA con acceso al contenido completo de libros.

---

# 24. Funcionalidades futuras de mayor interés

## Flashcards

Permitir revisar ideas guardadas anteriormente.

## Búsqueda

Buscar entre todos los aprendizajes acumulados.

## Widget

Iniciar rápidamente una sesión desde la pantalla principal del teléfono.

## Sincronización

Cuenta opcional para mantener datos entre dispositivos.

## Resumen de conocimiento

Permitir consultar:

> ¿Qué he aprendido sobre hábitos?

utilizando únicamente las notas personales guardadas por el usuario.

---

# 25. Entidades principales

Conceptualmente el producto necesita manejar:

### Libro

Lo que el usuario está leyendo.

### Sesión

Una ocasión en la que el usuario leyó.

### Idea

Algo que desea conservar de esa lectura.

### Acción

Una aplicación práctica asociada a una idea.

La relación principal será:

**Libro → Sesiones → Ideas → Acciones**

---

# 26. Métricas de éxito del MVP

El MVP será exitoso si después de aproximadamente 30 días el usuario siente que:

- Le cuesta menos empezar a leer.
- Lee con mayor frecuencia.
- Mantiene constancia durante varias semanas.
- Avanza de forma visible en sus libros.
- Termina libros que anteriormente hubiera abandonado.
- Puede recordar ideas relevantes de sus lecturas.
- Ha aplicado al menos algunas de esas ideas en su vida.

La métrica principal no debería ser:

> Minutos dentro de la aplicación.

Debe ser:

> **La aplicación está consiguiendo que el usuario lea.**

---

# 27. Definición resumida del MVP

El MVP tendrá cinco pilares:

### 📖 Leer

Sesiones rápidas con metas de páginas.

### 🔥 Mantener

Rachas, recuperación de rachas y pequeños logros.

### 📈 Avanzar

Seguimiento simple del progreso de cada libro.

### 💭 Recordar

Guardar ideas opcionalmente.

### ⚡ Aplicar

Convertir una idea en una acción concreta cuando tenga sentido.

---

# 28. Visión del producto

La aplicación no quiere convertir leer en una obligación ni en una actividad que deba medirse constantemente.

Su propósito es conseguir que pasar de:

> “Tengo varios libros que quiero leer.”

a:

> “Leer forma parte de mi día.”

sea lo más sencillo y agradable posible.

Y cuando una lectura tenga algo que merece conservarse:

> **Ayudar al usuario a llevar esa idea fuera del libro y ponerla en práctica.**