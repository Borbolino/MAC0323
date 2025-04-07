/******************************************************************************
 *
 * MAC0121 ALGORITMOS E ESTRUTURAS DE DADOS I
 * Aluno: João Pedro Barioni Agostini
 * Numero USP: 14582163
 * Tarefa: E05
 * Data: 03/12/2023
 * 
 * Baseado nas aulas dadas e em estudos. O programa define a classe TokenFinder,
 * utilizada para instanciar objetos do mesmo tipo, que transformam linhas de
 * arquivos do tipo CSV em tokens para fácil manipulação e maior controle.
 *
 * DECLARO QUE SOU O ÚNICO AUTOR E RESPONSÁVEL POR ESTE PROGRAMA.  TODAS AS 
 * PARTES DO PROGRAMA, EXCETO AS QUE SÃO BASEADAS EM MATERIAL FORNECIDO  
 * PELO PROFESSOR OU COPIADAS DO LIVRO OU DAS BIBLIOTECAS DE SEDGEWICK & WAYNE, 
 * FORAM DESENVOLVIDAS POR MIM.  DECLARO TAMBÉM QUE SOU RESPONSÁVEL POR TODAS 
 * AS CÓPIAS DESTE PROGRAMA E QUE NÃO DISTRIBUÍ NEM FACILITEI A DISTRIBUIÇÃO
 * DE CÓPIAS DESTA PROGRAMA.
 *
 ******************************************************************************/

class TokenFinder {

    private String[] tokenList; // CONTÉM TODOS OS TOKENS DE 'line'
    private int pointer = 0;    // APONTA O PRÓXIMO TOKEN PARA 'nextToken'
    
    public TokenFinder(String line) {
        int commaCounter = 0;   // CONTA A QUANTIDADE DE VÍRGULAS PARA CRAR UM VETOR DE TAMANHO SUFICIENTE
        for(int i = 1; i < line.length(); i++) {
            if(line.charAt(i) == ',') {
                commaCounter++;
            }
        }

        this.tokenList = new String[commaCounter+2];    // INICIALIZA O VETOR DE TAMANHO SUFICIENTE
        boolean inQuotes = false;                       // INDICA SE 'line.charAt(i)' ESTÁ ENTRE ASPAS
        boolean inToken = false;                        // INDICA SE 'line.charAt(i)' ESTÁ CONTIDO EM UM TOKEN
        int tokenStart = 0;                             // INDICA O ÍNDICE DE ÍNICIO DE UM TOKEN
        int tokenCount = 0;                             // INICA A QUANTIDADE DE TOKENS

        for(int i = 0; i < line.length(); i++) {

            // SE UM TOKEN É VAZIO, ADICIONA A STRING "" EM 'tokenList'
            if(i > 0 && line.charAt(i) == ',' && !inToken && !inQuotes && line.charAt(i-1) == ',') {
                this.tokenList[tokenCount] = "";
                tokenCount++;
                continue;
            }

            if(line.charAt(i) == '"') {         // DETERMINA O QUE FAZER COM O CARACTERE '"'
                if(line.charAt(i+1) != '"') {   // CASO 1 -> APENAS UMA ASPAS
                    inQuotes = !inQuotes;
                    line = line.substring(0, i) + line.substring(i+1, line.length());   // ELIMINA O CARACTERE '"'
                }
                else {                                                                  // CASO 2 -> DUAS ASPAS
                    line = line.substring(0, i) + line.substring(i+1, line.length());   // ELIMINA APENAS UMA DAS ASPAS
                    i++; 
                    continue;
                }
            }

            if(!inToken) { tokenStart = i; inToken = true; }                // INICIALIZA A LEITURA DO TOKEN

            if(line.charAt(i) == ',' && inToken && !inQuotes) {             // FINALIZA A LEITURA DO TOKEN
                this.tokenList[tokenCount] = line.substring(tokenStart, i); // ADICIONA EM "tokenList"
                tokenCount++;
                inToken = false;
            }
            
            // ADICIONA O ÚLTIMO TOKEN DE "line" EM "tokenList"
            if(i == line.length()-1 && line.charAt(i) != ',') {
                this.tokenList[tokenCount] = line.substring(tokenStart, i+1);
            }
        }
    }

    public String nextToken() {
        // RETORNA O PRÓXIMO TOKEN DE "tokenList" APONTADO POR "pointer"
        String token = this.tokenList[this.pointer];
        this.pointer++;
        return token;
    }
}