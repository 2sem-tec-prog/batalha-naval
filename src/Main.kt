//GUSTAVO DECKER COUTO
import kotlin.random.Random

fun criarTabuleiro(): Array<Array<Char>> {
    println("Informe o tamanho do tabuleiro")
    val tamanho = readln().toInt()
    //Cria o array de 10 linhas por 10 colunas com todos os valora vazio ' '
    val tabuleiro = Array(tamanho) { Array(tamanho) { ' ' } }
    //cria uma lista de navio que depois serão inserido no array
    val todosNavios = List(80) { 'P' } + List(10) {'C'} + List(10) { 'R' }

    //para cada navio na lista ele vai pegar uma linha e coluna aleatórios para alterar
    for (navio in todosNavios) {
        var vago = false
        while (!vago) {
            val lin = Random.nextInt(10) //pega numeros aleatorios
            val col = Random.nextInt(10)

            if (tabuleiro[lin][col] == ' ') { //se a coluna do array estiver vazio é inserido o navio
                tabuleiro[lin][col] = navio
                vago = true
            }
        }
    }
    return tabuleiro
}


fun imprimirTabuleiro(tabuleiro: Array<Array<Char>>) {
    //cores que serão usadas na estilização
    val reset = "\u001B[0m"
    val amarelo = "\u001B[33m"
    val vermelho = "\u001B[31m"
    val azul = "\u001B[34m"


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
                else -> print("|       ")
            }
        }
        println("|  $amarelo${i + 1}$reset") //numero de colunas
    }
    repeat(tabuleiro[0].size) {
        print("--------")
    }
    println("")


    for (i in tabuleiro[0].indices) { //mostra os numeros das colunas abaixo do tabuleiro
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

        if (ativar == 1) { //se o jogador inserir 1 ele começa o jogo
            val tabuleiro = criarTabuleiro()
            val tentativas = 3

            println("")
            imprimirTabuleiro(tabuleiro) //puxa a função que imprime o tabuleiro na tela

            var jogadas = 0
            var acertos = 0
            var soma = 0
            var cont = 0;

            val historicoX = arrayOfNulls<Int>(15)
            val historicoY = arrayOfNulls<Int>(15)

            while (jogadas < tentativas) {
                println("\nInsira as coordenadas da bomba")


                val tentativasSobrando = tentativas - jogadas //mostra quantas tentativas faltam
                println("Você tem mais $tentativasSobrando tentativas")

                print("X: ")
                val coordenadaX = readln().toIntOrNull()?.minus(1) //pega as coordenadas x
                historicoX[cont] = coordenadaX;
                print("Y: ")
                val coordenadaY = readln().toIntOrNull()?.minus(1) //pega as coordenadas y
                historicoY[cont] = coordenadaY
                cont++

                if (coordenadaX == -1 && coordenadaY == -1) { //encerra o jogo
                    println("Encerrando")
                    for (i in 0..cont - 1){
                        if (historicoX[i] != -1 && historicoY[i] != -1){
                            println("Jogada ${i+1}: X = ${historicoX[i]?.plus(1)}, Y = ${historicoY[i]?.plus(1)}")
                        }
                    }
                    break
                }
                if (coordenadaX == null || coordenadaX >= 10 || coordenadaX < 0) { //coordenadas maiores que o tabuleiro
                    print("Coordenadas inválidas")
                    continue
                }
                if (coordenadaY == null || coordenadaY >= 10 || coordenadaY < 0) { //coordenadas maiores que o tabuleiro
                    print("Coordenadas inválidas")
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

                //após todas as verificações vai imprimir o tabuleiro atualizado
                imprimirTabuleiro(tabuleiro)

                if (acertos == 13) { //verifica se o jogador acertou todos os navios
                    println("Parabéns você acertou todos os navios!!")
                    break
                }

                if (jogadas == tentativas-1) { //verifica se é a ultima jogada e mostra a pontuação
                    println("Pontuação total: $soma")
                    for (i in 0..cont - 1){
                        println("Jogada ${i+1}: X = ${historicoX[i]?.plus(1)}, Y = ${historicoY[i]?.plus(1)}")
                    }

                }
                jogadas++
            }
        } else if (ativar == 0) { //se o jogador inserir 0 no menu, fecha o jogo
            print("Encerrando")
            break
        } else {
            continue
        }
    }
}