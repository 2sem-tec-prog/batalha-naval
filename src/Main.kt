//GUSTAVO DECKER COUTO
import kotlin.random.Random

fun criarTabuleiro(): Array<Array<Char>> {
    val tabuleiro = Array(10) { Array(10) { ' ' } }
    val todosNavios = List(10) { 'P' } + List(1) { 'C' } + List(2) { 'R' }

    for (navio in todosNavios) {
        var vago = false
        while (!vago) {
            val lin = Random.nextInt(10)
            val col = Random.nextInt(10)

            if (tabuleiro[lin][col] == ' ') {
                tabuleiro[lin][col] = navio
                vago = true
            }
        }
    }
    return tabuleiro
}

fun imprimirTabuleiro(tabuleiro: Array<Array<Char>>) {
    val reset = "\u001B[0m"
    val amarelo = "\u001B[33m"
    val vermelho = "\u001B[31m"
    val azul = "\u001B[34m"

    for (i in tabuleiro.indices) {
        repeat(tabuleiro[i].size) {
        }

        for (c in tabuleiro[i].indices) {
            val celula = tabuleiro[i][c]
            when (celula) {
                'P' -> print("|   ${azul}P${reset}   ")
                'C' -> print("|   ${azul}C${reset}   ")
                'R' -> print("|   ${azul}R${reset}   ")
                'p' -> print("|   ${vermelho}p${reset}   ")
                'c' -> print("|   ${vermelho}c${reset}   ")
                'r' -> print("|   ${vermelho}r${reset}   ")
                '~' -> print("|   ${azul}~${reset}   ")
                else -> print("|       ")
            }
        }
        println("|  $amarelo${i + 1}$reset")
    }
    repeat(tabuleiro[0].size) {
    }
    println("")

    for (i in tabuleiro[0].indices) {
        val num = (i + 1).toString().padStart(2, ' ')
        print("   $amarelo$num$reset   ")
    }
    println()
}


fun main() {
    while (true) {
        println("\nIniciar o jogo - 1 \nEncerrar - 0 \nDesistir da partida - X:0 Y:0")

        println("\n||⛵ Batalha Naval ⛵||")
        print("     Escolha: ")
        val ativar = readln().toIntOrNull()

        if (ativar == 1) {
            val tabuleiro = criarTabuleiro()
            val tentativas = 15

            println("")
            imprimirTabuleiro(tabuleiro)

            var jogadas = 0
            var acertos = 0
            var soma = 0

            while (jogadas < tentativas) {
                println("\nInsira as coordenadas da bomba")

                val tentativasSobrando = tentativas - jogadas
                println("Você tem mais $tentativasSobrando tentativas")

                print("X: ")
                val coordenadaX = readln().toIntOrNull()?.minus(1)

                print("Y: ")
                val coordenadaY = readln().toIntOrNull()?.minus(1)


                if (coordenadaX == -1 && coordenadaY == -1) {
                    println("Encerrando")
                    break
                }
                if (coordenadaX == null || coordenadaX >= 10 || coordenadaX < 0) {
                    print("Coordenadas inválidas")
                    continue
                }
                if (coordenadaY == null || coordenadaY >= 10 || coordenadaY < 0) {
                    print("Coordenadas inválidas")
                    continue
                }

                val celulaUsada = setOf('p', 'r', 'c', '~', '1', '2', '3')
                if (tabuleiro[coordenadaX][coordenadaY] in celulaUsada) {
                    println("Coordenadas já foram utilizadas.")
                    continue
                }


                if (tabuleiro[coordenadaX][coordenadaY] == 'P') {
                    println("Alvo atingido! Porta-aviões abatido!")
                    acertos++
                    soma += 5
                    tabuleiro[coordenadaX][coordenadaY] = 'p'

                } else if (tabuleiro[coordenadaX][coordenadaY] == 'C') {
                    println("Alvo atingido! Cruzador abatido!")
                    acertos++
                    soma += 15
                    tabuleiro[coordenadaX][coordenadaY] = 'c'

                } else if (tabuleiro[coordenadaX][coordenadaY] == 'R') {
                    println("Alvo atingido! Rebocador abatido!")
                    acertos++
                    soma += 15
                    tabuleiro[coordenadaX][coordenadaY] = 'r'

                } else {
                    tabuleiro[coordenadaX][coordenadaY] = '~'
                }

                println("")

                imprimirTabuleiro(tabuleiro)

                if (acertos == 18) {
                    println("Parabéns você acertou todos os navios!!")
                    break
                }

                if (jogadas == tentativas - 1) {
                    println("Pontuação total: $soma")
                }
                jogadas++
            }
        } else if (ativar == 0) {
            print("Encerrando")
            break
        } else {
            continue
        }
    }
}