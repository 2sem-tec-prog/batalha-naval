//GUSTAVO DECKER COUTO
//GIOVANE MELO
//ESTEVÃO SANTOS
//GUSTAVO GONSALVES
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

fun criarTabuleiro(): Pair< Array<Array<Char>>, Int> {
    var tamanho: Int

    while(true) {
        println("Informe o tamanho do tabuleiro")
        tamanho = readln().toIntOrNull() ?: 0
        if (tamanho < 5 || tamanho > 15) {
            print("Valores inválidos.")
            continue
        }
        break
    }



    //Cria o array de 10 linhas por 10 colunas com todos os valora vazio' '
    val tabuleiro = Array(tamanho) { Array(tamanho) { ' ' } }
    //cria uma lista de navio que depois serão inserido no array
    var todosNavios:List<Char>
    while (true){
        println("Escolha:\n1 - Fácil\n2 - Intermediario\n3 - Difícil")

        val dificuldade = readln().toInt()

        when (dificuldade) {
            1 -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.2).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.15).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.07).roundToInt())) { 'R' }
                break
            }
            2 -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.1).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.02).roundToInt())) { 'R' }
                break
            }
            3 -> {
                todosNavios =
                    List(max(1, (tamanho * tamanho * 0.07).roundToInt())) { 'P' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'C' } +
                            List(max(1, (tamanho * tamanho * 0.01).roundToInt())) { 'R' }
                break
            }
            else -> {
                println("Entrada inválida")
                continue
            }
        }
    }



    //para cada navio na lista ele vai pegar uma linha e coluna aleatórios para alterar
    for (navio in todosNavios) {
        var vago = false
        while (!vago) {
            val lin = Random.nextInt(tamanho) //pega numeros aleatorios
            val col = Random.nextInt(tamanho)

            if (tabuleiro[lin][col] == ' ') { //se a coluna do array estiver vazio é inserido o navio
                tabuleiro[lin][col] = navio
                vago = true
            }
        }
    }
    return Pair(tabuleiro, tamanho)
}

fun imprimirTabuleiro(tabuleiro: Array<Array<Char>>) {
    //cores que serão usadas na estilização
    val reset = "\u001B[0m"
    val amarelo = "\u001B[33m"
    val vermelho = "\u001B[31m"
    val azul = "\u001B[34m"
    val verde = "\u001B[32m"


    for (i in tabuleiro.indices) {//para cada linha no tabuleiro

        repeat(tabuleiro[i].size) {
            print("--------")
        }
        println()
        for (c in tabuleiro[i].indices) { //para cada coluna dentro de uma linha
            val celula = tabuleiro[i][c] //val auxiliar
            when (celula) { //de acordo com o char do array, vai exibir a string na tela
                'P' -> print("|   ${azul}${reset}    ")
                'C' -> print("|   ${azul}${reset}    ")
                'R' -> print("|   ${azul}${reset}    ")
                'p' -> print("|   ${vermelho}p${reset}   ")
                'c' -> print("|   ${vermelho}c${reset}   ")
                'r' -> print("|   ${vermelho}r${reset}   ")
                '~' -> print("|   ${azul}${reset}   ")
                '1' -> print("|   ${verde}1${reset}   ")
                '2' -> print("|   ${verde}2${reset}   ")
                'M' -> print("|   ${verde}M${reset}   ")

                else -> print("|       ")
            }
        }
        println("|  $amarelo${i+1}$reset") //numero de colunas
    }
    repeat(tabuleiro[0].size) {
        print("--------")
    }
    println("")


    for (i in tabuleiro[0].indices) { //mostra os numeros das colunas abaixo do tabuleiro
        val num = (i+1).toString().padStart(2, ' ')
        print("   $amarelo$num$reset   ")
    }
    println()
}

fun revelarTabuleiro(tabuleiro: Array<Array<Char>>) {
    //cores que serão usadas na estilização
    val reset = "\u001B[0m"
    val amarelo = "\u001B[33m"
    val vermelho = "\u001B[31m"
    val azul = "\u001B[34m"
    val verde = "\u001B[32m"


    for (i in tabuleiro.indices) {//para cada linha no tabuleiro

        repeat(tabuleiro[i].size) {
            print("--------")
        }
        println()
        for (c in tabuleiro[i].indices) { //para cada coluna dentro de uma linha
            val celula = tabuleiro[i][c] //val auxiliar
            when (celula) { //de acordo com o char do array, vai exibir a string na tela
                'P' -> print("|   ${azul}P${reset}   ")
                'C' -> print("|   ${azul}C${reset}   ")
                'R' -> print("|   ${azul}R${reset}   ")
                'p' -> print("|   ${vermelho}p${reset}   ")
                'c' -> print("|   ${vermelho}c${reset}   ")
                'r' -> print("|   ${vermelho}r${reset}   ")
                '~' -> print("|   ${azul}${reset}   ")
                '1' -> print("|   ${verde}1${reset}   ")
                '2' -> print("|   ${verde}2${reset}   ")
                'M' -> print("|   ${verde}M${reset}   ")

                else -> print("|       ")
            }
        }
        println("|  $amarelo${i+1}$reset") //numero de colunas
    }
    repeat(tabuleiro[0].size) {
        print("--------")
    }
    println("")


    for (i in tabuleiro[0].indices) { //mostra os numeros das colunas abaixo do tabuleiro
        val num = (i+1).toString().padStart(2, ' ')
        print("   $amarelo$num$reset   ")
    }
    println()
}

fun distanciaMaisProxima(tabuleiro: Array<Array<Char>>, x: Int, y: Int): Int? {
    val opcoes = charArrayOf('P','p','C','c','R', 'r')

    for (dist in 1..3) {
        for (dx in -dist..dist) {
            for (dy in -dist..dist) {
                if (dx == 0 && dy == 0) continue

                val novoX = x + dx
                val novoY = y + dy


                if (novoX in tabuleiro.indices && novoY in tabuleiro[novoX].indices) {
                    val celula = tabuleiro[novoX][novoY]
                    if (celula in opcoes) {
                        return dist
                    }
                }
            }
        }
    }
    return null
}

fun main() {
    while (true) {
        println("\nIniciar o jogo - 1 \nEncerrar - 0 \nDesistir da partida - X:0 Y:0")

        println("\n||⛵ Batalha Naval ⛵||")
        print("     Escolha: ")
        val ativar = readln().toIntOrNull()

        if (ativar == 1) { //se o jogador inserir 1 ele começa o jogo
            val (tabuleiro, tamanho) = criarTabuleiro()
            val tentativas = (tamanho * 1.5).roundToInt()

            println("")
            imprimirTabuleiro(tabuleiro) //puxa a função que imprime o tabuleiro na tela

            var jogadas = 0
            var acertos = 0
            var soma = 0
            var cont = 0

            val historicoX = arrayOfNulls<Int>(tentativas+2)
            val historicoY = arrayOfNulls<Int>(tentativas+2)
            val historicoJogadas = arrayOfNulls<String>(tentativas+2)

            while (jogadas < tentativas) {
                println("\nInsira as coordenadas da bomba")


                val tentativasSobrando = tentativas - jogadas //mostra quantas tentativas faltam
                println("Você tem mais $tentativasSobrando tentativas")

                print("Y: ")
                val coordenadaX = readln().toIntOrNull()?.minus(1) //pega as coordenadas x
                historicoX[cont] = coordenadaX
                print("X: ")
                val coordenadaY = readln().toIntOrNull()?.minus(1) //pega as coordenadas y
                historicoY[cont] = coordenadaY
                cont++

                if (coordenadaX == -1 && coordenadaY == -1) { //encerra o jogo
                    println("")
                    println("Mapa Revelado:")
                    revelarTabuleiro(tabuleiro) //puxa a função que imprime o tabuleiro na tela
                    println("Encerrando...")
                    println("O total de acertos foi: $acertos")
                    println("O total de pontos foi: $soma")
                    println("------------------------------------------")
                    println("--------------- Historico ----------------")
                    for (i in 0..cont - 1){
                        if (historicoX[i] != -1 && historicoY[i] != -1){

                            println("Jogada ${i+1}: X = ${historicoX[i]?.plus(1)}, Y = ${historicoY[i]?.plus(1)} => ${historicoJogadas[i]}")

                        }
                    }
                    break
                }
                if (coordenadaX == null || coordenadaX >= tamanho || coordenadaX < 0) { //coordenadas maiores que o tabuleiro
                    print("Coordenadas inválidas")
                    cont--
                    continue
                }
                if (coordenadaY == null || coordenadaY >= tamanho || coordenadaY < 0) { //coordenadas maiores que o tabuleiro
                    print("Coordenadas inválidas")
                    cont--
                    continue
                }

                val celulaUsada = setOf('p','r','c','~','1','2','3') //confere se a bomba ja foi lançada nesse lugar
                if (tabuleiro[coordenadaX][coordenadaY] in celulaUsada) {
                    println("Coordenadas já foram utilizadas.")
                    continue
                }

                //verificação de acertos e soma de pontuação
                if (tabuleiro[coordenadaX][coordenadaY] == 'P') {
                    println("Alvo atingido! Porta-aviões abatido!")
                    acertos++
                    soma += 5
                    historicoJogadas[cont] = "Porta-aviões, 5 pontos"
                    tabuleiro[coordenadaX][coordenadaY] = 'p'

                } else if (tabuleiro[coordenadaX][coordenadaY] == 'C') {
                    println("Alvo atingido! Cruzador abatido!")
                    acertos++
                    soma += 15
                    historicoJogadas[cont] = "Cruzador, 15 pontos"
                    tabuleiro[coordenadaX][coordenadaY] = 'c'

                } else if (tabuleiro[coordenadaX][coordenadaY] == 'R') {
                    println("Alvo atingido! Rebocador abatido!")
                    acertos++
                    soma += 10
                    historicoJogadas[cont] = "Rebocador, 10 pontos"
                    tabuleiro[coordenadaX][coordenadaY] = 'r'

                } else {
                    tabuleiro[coordenadaX][coordenadaY] = '~'
                    val distancia = distanciaMaisProxima(tabuleiro, coordenadaX, coordenadaY)


                    if (distancia != null) {
                        when (distancia) {
                            1 -> {
                                tabuleiro[coordenadaX][coordenadaY] = '1'
                                println("Errou, mas está a $distancia casa de distância.")
                                historicoJogadas[cont] = "Água, 1 casa de distancia de um navio"
                            }
                            2 -> {
                                tabuleiro[coordenadaX][coordenadaY] = '2'
                                println("Errou, mas está a $distancia casa(s) de distância.")
                                historicoJogadas[cont] = "Água, 2 casas de distancia de um navio"
                            }
                            3 -> {
                                tabuleiro[coordenadaX][coordenadaY] = 'M'
                                println("Errou, está a $distancia ou mais casas de distância.")
                                historicoJogadas[cont] = "Água, 3 ou mais casas de distancia de um navio"
                            }
                        }
                    }
                }

                println("")

                //após todas as verificações vai imprimir o tabuleiro atualizado
                imprimirTabuleiro(tabuleiro)

                if (acertos == 13) { //verifica se o jogador acertou todos os navios
                    println("Parabéns você acertou todos os navios!!")
                    break
                }
                jogadas++
                if (jogadas == tentativas) { //verifica se é a ultima jogada e mostra a pontuação
                    println("")
                    println("Mapa Revelado:")
                    revelarTabuleiro(tabuleiro) //puxa a função que imprime o tabuleiro na tela
                    println("Encerrando...")
                    println("O total de acertos foi: $acertos")
                    println("O total de pontos foi: $soma")
                    println("------------------------------------------")
                    println("--------------- Historico ----------------")
                    for (i in 0..cont - 1){
                        if (historicoX[i] != -1 && historicoY[i] != -1){

                            println("Jogada ${i+1}: X = ${historicoX[i]?.plus(1)}, Y = ${historicoY[i]?.plus(1)} => ${historicoJogadas[i]}")

                        }
                    }

                }
            }
        } else if (ativar == 0) { //se o jogador inserir 0 no menu, fecha o jogo
            print("Encerrando")
            break
        } else {
            continue
        }
    }
}