data class Usuario(
    var nombre: String,
    var password: String,
    var saldo: Double
)

fun main() {
    val usuarios = mutableListOf(
        Usuario("Pedro", "1234", 100000.0),
        Usuario("Juan", "5678", 200000.0)
    )
    var usuario: Usuario? = null
    var opc: Int

    do {
        println("===== Cajero Automatico =====")
        println("1. Iniciar sesion / Registrarse")
        println("2. Girar dinero")
        println("3. Depositar dinero")
        println("4. Salir")
        print("Seleccione una opcion: ")

        opc = readln().toInt()

        when(opc){
            1 -> {
                println("Ingrese su nombre")
                val nombre = readLine().toString()
                val existente = usuarios.find { it.nombre == nombre }

                if (existente != null) {
                    print("Contraseña: ")
                    val password = readLine().orEmpty()
                    if (password == existente.password) {
                        usuario = existente
                        println("Inicio de sesión exitoso. Bienvenido ${usuario!!.nombre}")
                    } else {
                        println("Contraseña incorrecta.")
                    }
                } else {
                    println("Usuario no encontrado. Registrando nuevo usuario...")
                    print("Cree una contraseña: ")
                    val password = readln().toString()
                    print("Ingrese saldo inicial: ")
                    val saldo = readln().toDouble()

                    val nuevoUsuario = Usuario(nombre, password, saldo)
                    usuarios.add(nuevoUsuario)
                    usuario = nuevoUsuario
                    println("Usuario registrado exitosamente y sesión iniciada: ${usuario!!.nombre}")
                }
            }
            2 -> {
                if (usuario != null) {
                    print("Ingrese monto a retirar: ")
                    val monto = readln().toDouble()
                    if (monto > 0 && monto <= usuario!!.saldo) {
                        usuario!!.saldo -= monto
                        println("Retiro exitoso. Saldo actual: ${usuario!!.saldo}")
                    } else {
                        println("Fondos insuficientes o monto inválido.")
                    }
                } else {
                    println("Debe iniciar sesión primero.")
                }
            }
            3 -> {
                if (usuario != null) {
                    println("=== Ingreso de dinero ===")
                    println("1. Depositar en mi cuenta")
                    println("2. Depositar en otro usuario")
                    print("Seleccione una opción: ")
                    val subopc = readln().toInt()

                    when (subopc) {
                        1 -> {
                            print("Ingrese monto a depositar en su cuenta: ")
                            val monto = readln().toDouble()
                            if (monto > 0) {
                                usuario!!.saldo += monto
                                println("Depósito exitoso. Saldo actual: ${usuario!!.saldo}")
                            } else {
                                println("Monto inválido.")
                            }
                        }
                        2 -> {
                            println("Usuarios disponibles para depositar:")
                            val candidatos = usuarios.filter { it != usuario }
                            candidatos.forEachIndexed { i, u ->
                                println("${i + 1}. ${u.nombre} (Saldo: ${u.saldo})")
                            }
                            print("Seleccione usuario destino: ")
                            val index = readLine()?.toIntOrNull()
                            if (index != null && index in 1..candidatos.size) {
                                val destino = candidatos[index - 1]
                                print("Ingrese monto a depositar: ")
                                val monto = readln().toDouble()
                                if (monto > 0) {
                                    destino.saldo += monto
                                    println("Deposito exitoso en cuenta de ${destino.nombre}.")
                                    println("Saldo de ${destino.nombre}: ${destino.saldo}")
                                } else {
                                    println("Monto invalido.")
                                }
                            } else {
                                println("Seleccion invalida.")
                            }
                        }
                        else -> println("Opcion no válida.")
                    }
                } else {
                    println("Debe iniciar sesion primero.")
                }
            }
            4 -> {
                println("Saliendo del cajero...")
            }
        }
    }while(opc != 4)
}