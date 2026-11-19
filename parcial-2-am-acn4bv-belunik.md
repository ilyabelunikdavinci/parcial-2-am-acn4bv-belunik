PARCIAL 2 — APLICACIÓN “MIS GASTOS” https://github.com/ilyabelunikdavinci/parcial-2-am-acn4bv-belunik.git

1. Descripción general

“Mis Gastos” es una aplicación para registrar los gastos personales en el momento en que se realizan. El usuario carga una descripción, un monto y una categoría; la aplicación suma el total del mes y muestra un resumen por categoría con su porcentaje, para identificar en qué se gasta más y poder ahorrar.

La aplicación cuenta con dos pantallas:

Pantalla principal “Mis gastos” (MainActivity): registro de gastos y lista de movimientos.
Pantalla “Resumen por categoría” (ResumenActivity): totales y porcentajes por categoría.

Navegación: desde la pantalla principal, el botón “Ver resumen por categoría” abre la pantalla de resumen. El botón “Volver” (o el botón atrás del sistema) cierra el resumen y regresa a la pantalla principal, conservando los gastos cargados.

Las pantallas fueron desarrolladas utilizando ConstraintLayout como contenedor principal y diferentes LinearLayout para organizar los elementos de manera vertical y horizontal.

2. Pantalla principal — “Mis gastos”

\[Mock / Captura: pantalla principal con varios gastos cargados (1-principal.png)]

2.1. Elementos de la interfaz

Ícono de billetera: representa visualmente el objetivo de la aplicación.
“Mis gastos”: título principal de la pantalla.
“Gastos del mes”: indica el total acumulado.
Total: muestra la suma de los gastos registrados en formato argentino (por ejemplo, “$ 45.450,00”).
Campo “Descripción”: permite ingresar una descripción del gasto.
Campo “Monto del gasto”: permite introducir el importe; acepta punto o coma decimal.
Fila horizontal “Categoría”: contiene el texto “Categoría:”, un selector desplegable (Spinner) y la moneda “ARS”.
Campo “Comentario (opcional)”: aparece solamente cuando se elige la categoría “Otros”.
Botón “Agregar gasto”: permite registrar el nuevo movimiento.
Botón “Ver resumen por categoría”: abre la segunda pantalla.
Sección “Movimientos”: lista con desplazamiento (ScrollView) donde se muestran los gastos registrados.

2.2. Funcionalidad

El usuario elige una categoría: Comida, Transporte, Servicios, Salud, Educación, Ocio u Otros. La lista de categorías está definida en strings.xml.

Al elegir “Otros” aparece el campo de comentario; al elegir otra categoría el campo se oculta y se borra.

\[Mock / Captura: categoría “Otros” con el campo de comentario visible (2-otros.png)]

Al presionar el botón “Agregar gasto”, la aplicación:

Verifica que la descripción y el monto no estén vacíos y que el monto sea mayor a 0. Si hay un error, lo indica en el campo correspondiente y no agrega el gasto.
Obtiene la descripción, el monto, la categoría y el comentario (si lo hay).
Crea dinámicamente un nuevo elemento para representar el movimiento (por ejemplo, “Comida · Pan $ 450,00”).
Agrega el nuevo movimiento a la lista.
Actualiza el total acumulado de los gastos.
Guarda la categoría y el monto para el resumen.
Limpia los campos, vuelve la categoría a “Comida” y muestra el mensaje “Gasto agregado”.

\[Mock / Captura: mensaje de error por campo vacío (3-error.png)]

2.3. Flujo de uso

El usuario inicia la aplicación.
Se muestra el total actual de gastos.
El usuario ingresa la descripción del gasto.
Ingresa el monto correspondiente.
Elige la categoría y, si es “Otros”, puede escribir un comentario.
Presiona el botón “Agregar gasto”.
El nuevo movimiento aparece en la sección “Movimientos”.
El total acumulado se actualiza automáticamente.
El usuario puede continuar agregando nuevos gastos o presionar “Ver resumen por categoría”.

3. Pantalla de resumen — “Resumen por categoría”

\[Mock / Captura: pantalla de resumen con íconos y barras (4-resumen.png)]

3.1. Elementos de la interfaz

“Resumen por categoría”: título de la pantalla.
“Total del mes”: total acumulado de todos los gastos.
Lista de categorías con desplazamiento (ScrollView). Por cada categoría:
Fila horizontal con el ícono de la categoría, el nombre con su porcentaje y el monto.
Barra de progreso (ProgressBar) proporcional al porcentaje de la categoría.
Botón “Volver”: regresa a la pantalla principal.

3.2. Funcionalidad

Recibe los gastos desde la pantalla principal.
Suma los montos de cada categoría y calcula su porcentaje sobre el total.
Muestra un ícono distinto para cada categoría (Vector Assets en res/drawable).
Si todavía no hay gastos cargados, muestra el mensaje “Todavía no hay gastos cargados”.

3.3. Flujo de uso

El usuario llega desde el botón “Ver resumen por categoría”.
Ve el total y cuánto gastó en cada categoría, con su porcentaje y su barra.
Presiona “Volver” para seguir cargando gastos.

4. Comportamiento dinámico

La aplicación presenta comportamiento dinámico en ambas pantallas:

Cada vez que el usuario registra un gasto, se crea un nuevo elemento visual mediante Java y se incorpora a la lista de movimientos dentro de un ScrollView. Al mismo tiempo, se modifica el total acumulado.
El campo de comentario se muestra u oculta según la categoría elegida.
En la pantalla de resumen, las filas de cada categoría (ícono, texto y barra) se crean mediante Java a partir de los datos recibidos.

5. Pasaje de datos entre Activities

Al presionar “Ver resumen por categoría”, la pantalla principal crea un Intent y le agrega extras:

La lista de categorías: putStringArrayListExtra("categorias", ...).
Los montos: putExtra("montos", ...) con un arreglo double\[].
El total: putExtra("total", ...).

ResumenActivity recupera estos datos con getStringArrayListExtra, getDoubleArrayExtra y getDoubleExtra.

6. Componentes y recursos utilizados

Layouts: ConstraintLayout, LinearLayout vertical y LinearLayout horizontal.
Componentes: TextView, EditText, Button, Spinner, ImageView, ScrollView, ProgressBar.
Eventos: clic en “Agregar gasto”, “Ver resumen por categoría” y “Volver”; selección de categoría en el Spinner (OnItemSelectedListener).
Recursos: lista de categorías en strings.xml; íconos de categorías como Vector Assets en res/drawable.
Commits: se utilizó la convención Conventional Commits (feat:, fix:, docs:).

7. Mejoras futuras

Guardar los gastos en el teléfono o en Firebase Firestore para que no se pierdan al cerrar la aplicación.
Agregar ingresos y mostrar el saldo (ingresos − gastos).
Filtrar los gastos por mes.

