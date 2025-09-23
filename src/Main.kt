// Alunos: Gustavo Decker Couto, Giovane Melo, Estevão Santos, Gustavo Gonsalves
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

const val reset = "\u001B[0m"
const val amarelo = "\u001B[33m"
const val vermelho = "\u001B[31m"
const val azul = "\u001B[34m"
const val verde = "\u001B[32m"

fun configurarJogo(): Pair<Array<Array<Char>>, Int> {
    val tamanho = obterTamanhoTabuleiro()
    val frota = obterFrotaPorDificuldade(tamanho)
    val tabuleiro = Array(tamanho) { Array(tamanho) { ' ' } }
    posicionarNaviosAleatoriamente(tabuleiro, frota)
    return Pair(tabuleiro, tamanho)
}

fun obterTamanhoTabuleiro(): Int {
    var tamanho: Int
    while (true) {
        println("Informe o tamanho do tabuleiro (5 a 15): ")
        tamanho = readln().toIntOrNull() ?: 0
        if (tamanho in 5..15) return tamanho
        println("Valor inválido. Tente novamente.")
    }
}

fun gerarFrota(tamanho: Int, pesoP: Double, pesoC: Double, pesoR: Double): List<Char> {
    val area = tamanho * tamanho
    return List(max(1, (area * pesoP).roundToInt())) { 'P' } +
            List(max(1, (area * pesoC).roundToInt())) { 'C' } +
            List(max(1, (area * pesoR).roundToInt())) { 'R' }
}

fun obterFrotaPorDificuldade(tamanho: Int): List<Char> {
    while (true) {
        println("Escolha a dificuldade:\n1 - Fácil\n2 - Intermediário\n3 - Difícil")
        when (readln().toIntOrNull()) {
            1 -> return gerarFrota(tamanho, 0.2, 0.15, 0.07)
            2 -> return gerarFrota(tamanho, 0.1, 0.01, 0.02)
            3 -> return gerarFrota(tamanho, 0.07, 0.01, 0.01)
            else -> println("Opção inválida, tente novamente.")
        }
    }
}

fun posicionarNaviosAleatoriamente(tabuleiro: Array<Array<Char>>, frota: List<Char>) {
    for (navio in frota) {
        var colocado = false
        while (!colocado) {
            val lin = Random.nextInt(tabuleiro.size)
            val col = Random.nextInt(tabuleiro.size)
            if (tabuleiro[lin][col] == ' ') {
                tabuleiro[lin][col] = navio
                colocado = true
            }
        }
    }
}

fun formatarCelula(
    celula: Char, reset: String, vermelho: String, azul: String, verde: String
): String {
    return when (celula) {
        'P', 'C', 'R' -> "   $azul $reset   "
        'p' -> "   ${vermelho}p$reset   "
        'c' -> "   ${vermelho}c$reset   "
        'r' -> "   ${vermelho}r$reset   "
        '~' -> "   $azul~$reset   "
        '1' -> "   ${verde}1$reset   "
        '2' -> "   ${verde}2$reset   "
        'M' -> "   ${verde}M$reset   "
        else -> "       "
    }
}

fun imprimirLinhaSeparadora(tamanho: Int) {
    repeat(tamanho) { print("--------") }
    println()
}

fun imprimirIndicesInferiores(tamanho: Int, amarelo: String, reset: String) {
    for (i in 0 until tamanho) {
        val num = (i + 1).toString().padStart(2, ' ')
        print("   $amarelo$num$reset   ")
    }
    println()
}

fun imprimirTabuleiro(tabuleiro: Array<Array<Char>>) {
    for (i in tabuleiro.indices) {
        imprimirLinhaSeparadora(tabuleiro[i].size)
        for (c in tabuleiro[i].indices) {
            val simbolo = formatarCelula(tabuleiro[i][c], reset, vermelho, azul, verde)
            print("|$simbolo")
        }
        println("|  $amarelo${i + 1}$reset")
    }
    imprimirLinhaSeparadora(tabuleiro[0].size)
    imprimirIndicesInferiores(tabuleiro[0].size, amarelo, reset)
}

fun distanciaMaisProxima(tabuleiro: Array<Array<Char>>, x: Int, y: Int): Int? {
    val navios = charArrayOf('P', 'p', 'C', 'c', 'R', 'r')
    for (dist in 1..3) {
        for (dx in -dist..dist) {
            for (dy in -dist..dist) {
                if (dx == 0 && dy == 0) continue
                val novoX = x + dx
                val novoY = y + dy
                if (novoX in tabuleiro.indices && novoY in tabuleiro[novoX].indices) {
                    if (tabuleiro[novoX][novoY] in navios) return dist
                }
            }
        }
    }
    return null
}

fun tratarAcerto(tabuleiro: Array<Array<Char>>, x: Int, y: Int, historico: MutableList<String>): Int {
    return when (tabuleiro[x][y]) {
        'P' -> { tabuleiro[x][y] = 'p'; historico += "Acertou Porta-aviões (+5 pontos)"; 5 }
        'C' -> { tabuleiro[x][y] = 'c'; historico += "Acertou Cruzador (+15 pontos)"; 15 }
        'R' -> { tabuleiro[x][y] = 'r'; historico += "Acertou Rebocador (+10 pontos)"; 10 }
        else -> 0
    }
}

fun tratarErro(tabuleiro: Array<Array<Char>>, x: Int, y: Int, historico: MutableList<String>): Int {
    val distancia = distanciaMaisProxima(tabuleiro, x, y)
    when (distancia) {
        1 -> { tabuleiro[x][y] = '1'; historico += "Água, mas a 1 casa de um navio" }
        2 -> { tabuleiro[x][y] = '2'; historico += "Água, mas a 2 casas de um navio" }
        3 -> { tabuleiro[x][y] = 'M'; historico += "Água, a 3+ casas de distância" }
        else -> { tabuleiro[x][y] = '~'; historico += "Água, longe de qualquer navio" }
    }
    return 0
}

fun processarJogada(
    tabuleiro: Array<Array<Char>>,
    x: Int,
    y: Int,
    historico: MutableList<String>
): Int {
    return when (tabuleiro[x][y]) {
        'P', 'C', 'R' -> tratarAcerto(tabuleiro, x, y, historico)
        else -> tratarErro(tabuleiro, x, y, historico)
    }
}

fun exibirResumoPartida(acertos: Int, pontos: Int, historico: List<String>) {
    println("Fim de jogo!")
    println("Total de acertos: $acertos")
    println("Pontuação final: $pontos")
    println("--------------- Histórico ----------------")
    historico.forEachIndexed { i, h -> println("Jogada ${i + 1}: $h") }
}

fun lerCoordenadas(tabuleiro: Array<Array<Char>>): Pair<Int, Int>? {
    print("Digite coordenada X: ")
    val x = readln().toIntOrNull()?.minus(1) ?: return null
    print("Digite coordenada Y: ")
    val y = readln().toIntOrNull()?.minus(1) ?: return null

    if (x !in tabuleiro.indices || y !in tabuleiro.indices) {
        println("Coordenadas inválidas.")
        return null
    }

    return x to y
}

fun mostrarStatusRodada(tabuleiro: Array<Array<Char>>, jogada: Int, tentativas: Int) {
    imprimirTabuleiro(tabuleiro)
    println("Tentativa $jogada de $tentativas")
}

fun realizarJogada(
    tabuleiro: Array<Array<Char>>,
    historico: MutableList<String>
): Pair<Int, Int>? {
    val coordenadas = lerCoordenadas(tabuleiro) ?: return null
    val (x, y) = coordenadas

    val pontos = processarJogada(tabuleiro, x, y, historico)
    val acertos = if (tabuleiro[x][y] in listOf('p', 'c', 'r')) 1 else 0

    return pontos to acertos
}

fun processarRodadas(
    tabuleiro: Array<Array<Char>>, tentativas: Int, historico: MutableList<String>
): Pair<Int, Int> {
    var pontos = 0
    var acertos = 0

    for (jogada in 1..tentativas) {
        mostrarStatusRodada(tabuleiro, jogada, tentativas)

        val resultado = realizarJogada(tabuleiro, historico) ?: continue
        pontos += resultado.first
        acertos += resultado.second

        if (acertos == 13) {
            println("Parabéns! Você destruiu todos os navios!")
            break
        }
    }

    return pontos to acertos
}

fun iniciarJogo() {
    val (tabuleiro, tamanho) = configurarJogo()
    val tentativas = (tamanho * 1.5).roundToInt()
    val historico = mutableListOf<String>()

    val (pontos, acertos) = processarRodadas(tabuleiro, tentativas, historico)

    imprimirTabuleiro(tabuleiro)
    exibirResumoPartida(acertos, pontos, historico)
}

fun main() {
    while (true) {
        println("\n||⛵ Batalha Naval ⛵||")
        println("1 - Iniciar o jogo")
        println("0 - Encerrar")
        when (readln().toIntOrNull()) {
            1 -> iniciarJogo()
            0 -> { println("Encerrando..."); break }
            else -> println("Opção inválida.")
        }
    }
}
